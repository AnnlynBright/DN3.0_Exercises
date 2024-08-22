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


CREATE OR REPLACE TRIGGER CheckTransactionRules
BEFORE INSERT ON Transactions
FOR EACH ROW
DECLARE
    v_balance Accounts.Balance%TYPE;
BEGIN
    -- Fetch the current balance of the account
    SELECT Balance INTO v_balance
    FROM Accounts
    WHERE AccountID = :NEW.AccountID;
    
    -- Check if the transaction is a withdrawal and ensure it does not exceed the balance
    IF :NEW.TransactionType = 'Withdrawal' THEN
        IF :NEW.Amount > v_balance THEN
            RAISE_APPLICATION_ERROR(-20001, 'Withdrawal amount exceeds account balance.');
        END IF;
    END IF;
    
    -- Check if the transaction is a deposit and ensure the amount is positive
    IF :NEW.TransactionType = 'Deposit' THEN
        IF :NEW.Amount <= 0 THEN
            RAISE_APPLICATION_ERROR(-20002, 'Deposit amount must be positive.');
        END IF;
    END IF;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20003, 'Account ID ' || :NEW.AccountID || ' does not exist.');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20004, 'An unexpected error occurred: ' || SQLERRM);
END;
/

-- Insert a test record with a valid withdrawal
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (4, 1, SYSDATE, 500, 'Withdrawal');

-- Insert a test record with a valid deposit
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (5, 2, SYSDATE, 1000, 'Deposit');

-- Insert a test record with a withdrawal exceeding balance
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (6, 1, SYSDATE, 1500, 'Withdrawal'); -- This should raise an error

-- Insert a test record with a negative deposit
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (7, 2, SYSDATE, -200, 'Deposit'); -- This should raise an error
