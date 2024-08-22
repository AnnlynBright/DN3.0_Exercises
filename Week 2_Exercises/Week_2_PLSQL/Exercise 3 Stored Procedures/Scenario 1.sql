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

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest
IS
BEGIN
    -- Update the balance of all savings accounts by applying 1% interest
    UPDATE Accounts
    SET Balance = Balance * 1.01,  -- Apply 1% interest
        LastModified = SYSDATE
    WHERE AccountType = 'Savings';

    -- Commit the transaction to make changes permanent
    COMMIT;

    -- Output the result
    DBMS_OUTPUT.PUT_LINE('Monthly interest has been processed for all savings accounts.');
EXCEPTION
    WHEN OTHERS THEN
        -- Handle any unexpected errors
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
        ROLLBACK;  -- Rollback changes in case of error
END;
/

BEGIN
    ProcessMonthlyInterest;
END;
/