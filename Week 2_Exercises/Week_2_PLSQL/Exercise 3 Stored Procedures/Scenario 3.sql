BEGIN
    -- Create Customers table
    EXECUTE IMMEDIATE '
        CREATE TABLE Customers (
            CustomerID NUMBER PRIMARY KEY,
            Name VARCHAR2(100),
            DOB DATE,
            Balance NUMBER,
            LastModified DATE
        )
    ';

    -- Create Accounts table
    EXECUTE IMMEDIATE '
        CREATE TABLE Accounts (
            AccountID NUMBER PRIMARY KEY,
            CustomerID NUMBER,
            AccountType VARCHAR2(20),
            Balance NUMBER,
            LastModified DATE,
            FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
        )
    ';

    -- Create Transactions table
    EXECUTE IMMEDIATE '
        CREATE TABLE Transactions (
            TransactionID NUMBER PRIMARY KEY,
            AccountID NUMBER,
            TransactionDate DATE,
            Amount NUMBER,
            TransactionType VARCHAR2(10),
            FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)
        )
    ';

    -- Create Loans table
    EXECUTE IMMEDIATE '
        CREATE TABLE Loans (
            LoanID NUMBER PRIMARY KEY,
            CustomerID NUMBER,
            LoanAmount NUMBER,
            InterestRate NUMBER,
            StartDate DATE,
            EndDate DATE,
            FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
        )
    ';

    -- Create Employees table
    EXECUTE IMMEDIATE '
        CREATE TABLE Employees (
            EmployeeID NUMBER PRIMARY KEY,
            Name VARCHAR2(100),
            Position VARCHAR2(50),
            Salary NUMBER,
            Department VARCHAR2(50),
            HireDate DATE
        )
    ';
END;
/

BEGIN
    -- Insert data into Customers table
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (1, 'John Doe', TO_DATE('1955-05-15', 'YYYY-MM-DD'), 1000, SYSDATE);

    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (2, 'Jane Smith', TO_DATE('1990-07-20', 'YYYY-MM-DD'), 1500, SYSDATE);

    -- Insert data into Accounts table
    INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
    VALUES (1, 1, 'Savings', 1000, SYSDATE);

    INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
    VALUES (2, 2, 'Checking', 1500, SYSDATE);

    -- Insert data into Transactions table
    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (1, 1, SYSDATE, 200, 'Deposit');

    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (2, 2, SYSDATE, 300, 'Withdrawal');

    -- Insert data into Loans table
    INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
    VALUES (1, 1, 5000, 5, SYSDATE, ADD_MONTHS(SYSDATE, 60));

    INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
    VALUES (2, 2, 10000, 4, SYSDATE, ADD_MONTHS(SYSDATE, 36));

    -- Insert data into Employees table
    INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
    VALUES (1, 'Alice Johnson', 'Manager', 70000, 'HR', TO_DATE('2015-06-15', 'YYYY-MM-DD'));

    INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
    VALUES (2, 'Bob Brown', 'Developer', 60000, 'IT', TO_DATE('2017-03-20', 'YYYY-MM-DD'));

    -- Commit the transactions
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE TransferFunds (
    p_source_account_id IN Accounts.AccountID%TYPE,
    p_target_account_id IN Accounts.AccountID%TYPE,
    p_amount IN NUMBER
) AS
    v_source_balance Accounts.Balance%TYPE;
    v_target_balance Accounts.Balance%TYPE;
BEGIN
    -- Check if the source account has sufficient balance
    SELECT Balance INTO v_source_balance
    FROM Accounts
    WHERE AccountID = p_source_account_id;
    
    IF v_source_balance < p_amount THEN
        -- If insufficient funds, raise an exception
        RAISE_APPLICATION_ERROR(-20001, 'Insufficient balance in source account.');
    END IF;

    -- Fetch the current balance of the target account
    SELECT Balance INTO v_target_balance
    FROM Accounts
    WHERE AccountID = p_target_account_id;

    -- Perform the fund transfer
    UPDATE Accounts
    SET Balance = Balance - p_amount
    WHERE AccountID = p_source_account_id;
    
    UPDATE Accounts
    SET Balance = Balance + p_amount
    WHERE AccountID = p_target_account_id;

    -- Commit the changes
    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Funds transferred successfully.');

EXCEPTION
    WHEN OTHERS THEN
        -- Rollback the transaction in case of an error
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END;
/

BEGIN
    -- Example: Transfer $100 from account 1 to account 2
    TransferFunds(p_source_account_id => 1, p_target_account_id => 2, p_amount => 100);
    
    -- Example: Attempt transfer with insufficient funds
    TransferFunds(p_source_account_id => 1, p_target_account_id => 2, p_amount => 5000);
END;
/

