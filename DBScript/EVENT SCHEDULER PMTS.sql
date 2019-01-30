use scgj_pmts;
SET GLOBAL event_scheduler = ON;
delimiter |

CREATE EVENT set_application_status_to_delayed
    ON SCHEDULE
      EVERY 24 Hour
    DO
      BEGIN
      
      update doc_status set doc_status.status="Delayed" where (doc_status.status="Assigned" OR doc_status.status="In Action" OR doc_status.status="On Hold") AND doc_status.eta<CURDATE();
      END |

delimiter ;