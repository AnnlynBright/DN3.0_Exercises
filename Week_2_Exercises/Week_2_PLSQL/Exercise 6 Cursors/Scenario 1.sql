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


DECLARE
    -- Cursor to select transactions for the current month
    CURSOR GenerateMonthlyStatements IS
        SELECT 
            t.TransactionID,
            t.AccountID,
            t.TransactionDate,
            t.Amount,
            t.TransactionType,
            a.CustomerID,
            c.Name
        FROM 
            Transactions t
            JOIN Accounts a ON t.AccountID = a.AccountID
            JOIN Customers c ON a.CustomerID = c.CustomerID
        WHERE 
            t.TransactionDate >= TRUNC(SYSDATE, 'MONTH') 
            AND t.TransactionDate < TRUNC(SYSDATE, 'MONTH') + INTERVAL '1' MONTH;
    
    -- Record type to hold cursor data
    v_record GenerateMonthlyStatements%ROWTYPE;
    
    -- Variable to hold current customer ID
    v_current_customer_id Customers.CustomerID%TYPE;
    
    -- Variable to track if we need to print a new statement
    v_first_record BOOLEAN := TRUE;

BEGIN
    -- Open the cursor
    OPEN GenerateMonthlyStatements;

    -- Loop through the cursor
    LOOP
        FETCH GenerateMonthlyStatements INTO v_record;
        EXIT WHEN GenerateMonthlyStatements%NOTFOUND;

        -- Check if we are processing a new customer
        IF v_first_record OR v_record.CustomerID != v_current_customer_id THEN
            -- Print a statement for the previous customer if this is not the first record
            IF NOT v_first_record THEN
                DBMS_OUTPUT.PUT_LINE('--------------------------');
                DBMS_OUTPUT.PUT_LINE('End of Statement for Customer ID: ' || v_current_customer_id);
                DBMS_OUTPUT.PUT_LINE('--------------------------');
            END IF;
            
            -- Print a new statement header for the current customer
            DBMS_OUTPUT.PUT_LINE('--------------------------');
            DBMS_OUTPUT.PUT_LINE('Monthly Statement for Customer ID: ' || v_record.CustomerID);
            DBMS_OUTPUT.PUT_LINE('Customer Name: ' || v_record.Name);
            DBMS_OUTPUT.PUT_LINE('--------------------------');
            
            -- Update the current customer ID and reset the flag
            v_current_customer_id := v_record.CustomerID;
            v_first_record := FALSE;
        END IF;
        
        -- Print the transaction details
        DBMS_OUTPUT.PUT_LINE('Transaction ID: ' || v_record.TransactionID);
        DBMS_OUTPUT.PUT_LINE('Account ID: ' || v_record.AccountID);
        DBMS_OUTPUT.PUT_LINE('Date: ' || v_record.TransactionDate);
        DBMS_OUTPUT.PUT_LINE('Amount: ' || v_record.Amount);
        DBMS_OUTPUT.PUT_LINE('Type: ' || v_record.TransactionType);
        DBMS_OUTPUT.PUT_LINE('--------------------------');
    END LOOP;

    -- Print the final statement for the last customer
    IF NOT v_first_record THEN
        DBMS_OUTPUT.PUT_LINE('--------------------------');
        DBMS_OUTPUT.PUT_LINE('End of Statement for Customer ID: ' || v_current_customer_id);
        DBMS_OUTPUT.PUT_LINE('--------------------------');
    END IF;

    -- Close the cursor
    CLOSE GenerateMonthlyStatements;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
        IF GenerateMonthlyStatements%ISOPEN THEN
            CLOSE GenerateMonthlyStatements;
        END IF;
END;
/

