CREATE OR REPLACE PROCEDURE SafeTransferFunds(
    p_FromAccountID IN Accounts.AccountID%TYPE,
    p_ToAccountID IN Accounts.AccountID%TYPE,
    p_Amount IN NUMBER
) 
IS
    v_FromBalance Accounts.Balance%TYPE;
    v_ToBalance Accounts.Balance%TYPE;
    insufficient_funds EXCEPTION;
BEGIN
    -- Lock both accounts to prevent race conditions
    SELECT Balance INTO v_FromBalance FROM Accounts WHERE AccountID = p_FromAccountID FOR UPDATE;
    SELECT Balance INTO v_ToBalance FROM Accounts WHERE AccountID = p_ToAccountID FOR UPDATE;

    -- Check if the 'from' account has sufficient funds
    IF v_FromBalance < p_Amount THEN
        RAISE insufficient_funds;
    END IF;

    -- Perform the transfer
    UPDATE Accounts
    SET Balance = Balance - p_Amount,
        LastModified = SYSDATE
    WHERE AccountID = p_FromAccountID;

    UPDATE Accounts
    SET Balance = Balance + p_Amount,
        LastModified = SYSDATE
    WHERE AccountID = p_ToAccountID;

    -- Commit the transaction
    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Transfer successful: ' || p_Amount || 
                         ' transferred from Account ' || p_FromAccountID || 
                         ' to Account ' || p_ToAccountID);

EXCEPTION
    WHEN insufficient_funds THEN
        -- Log error for insufficient funds
        DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds in Account ' || p_FromAccountID);
        ROLLBACK;
    
    WHEN OTHERS THEN
        -- Log any other errors and rollback
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END SafeTransferFunds;
/
