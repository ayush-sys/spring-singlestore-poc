DELIMITER //

CREATE OR REPLACE PROCEDURE update_waitover_flag(json_data JSON)
AS
    DECLARE msisdn BIGINT;
    DECLARE cam_id VARCHAR(255);
    DECLARE transaction_id VARCHAR(255);

BEGIN
    -- Extract values from JSON
    SET msisdn = JSON_VALUE(json_data, '$.MSISDN');
    SET cam_id = JSON_VALUE(json_data, '$.CAM_ID');
    SET transaction_id = JSON_VALUE(json_data, '$.TRANSACTION_ID');

    -- Update the waitover_flag in the table
    UPDATE RC_T_FACT
    SET waitover_flag = 1
    WHERE MSISDN = msisdn
      AND CAM_ID = cam_id
      AND TRANSACTION_ID = transaction_id;
END //

DELIMITER ;



------------------------------------------------------------------------------------------------------------------------------------------------


