INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (1, 'Stat_001', 'Status', 'Unresolved', 1, 1);
INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (2, 'Stat_002', 'Status', 'Resolved', 1, 2);
INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (3, 'EAP_001', 'EAP', 'Not Applicable', 1, 1);
INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (4, 'EAP_002', 'EAP', 'Yes, Referred', 1, 2);
INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (5, 'EAP_003', 'EAP', 'Yes, JBS EAP', 1, 3);
INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (6, 'EAP_004', 'EAP', 'No, Declined', 1, 4);
INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (7, 'RD_AT_001', 'Request_Doc_Attach_Type', 'Medical Certificate', 1, 1);
INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (8, 'RD_AT_002', 'Request_Doc_Attach_Type', 'Specialist Clearance', 1, 2);
INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (9, 'RD_AT_003', 'Request_Doc_Attach_Type', 'Stat declaration', 1, 3);
INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (10, 'RD_AT_004', 'Request_Doc_Attach_Type', 'Receipt', 1, 4);
INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (11, 'RD_AT_005', 'Request_Doc_Attach_Type', 'Funeral notice', 1, 5);
INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (12, 'RD_AT_006', 'Request_Doc_Attach_Type', 'Penalty notice', 1, 6);
INSERT INTO event_admin(id, code, type, value, status, sequence) VALUES (13, 'RD_AT_007', 'Request_Doc_Attach_Type', 'Other', 1, 7);

INSERT INTO event_category(id, code, name, status, sequence) VALUES (1, 'Cat_001', 'Attendance', 1, 1);
INSERT INTO event_category(id, code, name, status, sequence) VALUES (2, 'Cat_002', 'Other', 1, 2);

INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (1, 'AC', 'Absent Call In', 1, 1, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (2, 'AB', 'Absent', 1, 2, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (3, 'AA', 'Authorised Absence', 1, 3, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (4, 'LA', 'Leave of Absence', 1, 4, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (5, 'MS', 'Missed Swipe', 1, 5, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (6, 'SD', 'Stood Down', 1, 6, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (7, 'SL', 'Started Late', 1, 7, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (8, 'UA', 'Unauthorised Absence', 1, 8, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (9, 'WH', 'Went Home', 1, 9, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (10, 'CL', 'Compassionate Leave', 1, 10, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (11, 'AL', 'Annual Leave', 1, 11, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (12, 'LM', 'Leave Maternity', 1, 12, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (13, 'LP', 'Leave Paternity', 1, 13, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (14, 'LS', 'Long Service Leave', 1, 14, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (15, 'PC', 'Personal/Carer Leave - with cert', 1, 15, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (16, 'PCN', 'Personal/Carer Leave - without cert', 1, 16, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (17, 'WC', 'Workers Comp', 1, 17, 1);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (19, 'MC', 'Medical Certificate', 1, 1, 2);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (20, 'LC', 'Lost Card', 1, 2, 2);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (21, 'BTC', 'Breathalyser Test - All Clear (Less than 0.05%)', 1, 3, 2);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (22, 'BTP', 'Breathalyser Test - Positive (0.05% or greater)', 1, 4, 2);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (23, 'DAC', 'Drug Test - All Clear', 1, 5, 2);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (24, 'DCP', 'Drug Test - Confirmed Positive', 1, 6, 2);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (25, 'DNN', 'Drug Test - Non Negative', 1, 7, 2);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (26, 'SI', 'Suitable Duties in the Team', 1, 8, 2);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (27, 'SO', 'Suitable Duties Above the Team', 1, 9, 2);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (28, 'GR', 'Grievance', 1, 10, 2);
INSERT INTO event_type(id, code, name, status, sequence, category_id) VALUES (29, 'FN', 'File Note', 1, 11, 2);



INSERT INTO performance_admin(id, code, type, value, status, sequence) VALUES (1, 'Resp_001', 'Support_Response', 'Yes', 1, 2);
INSERT INTO performance_admin(id, code, type, value, status, sequence) VALUES (2, 'Resp_002', 'Support_Response', 'No', 1, 1);
INSERT INTO performance_admin(id, code, type, value, status, sequence) VALUES (3, 'Resp_003', 'Support_Response', 'Declined', 1, 3);
INSERT INTO performance_admin(id, code, type, value, status, sequence) VALUES (4, 'Interpreter_001', 'Interpreter', 'Yes', 1, 2);
INSERT INTO performance_admin(id, code, type, value, status, sequence) VALUES (5, 'Interpreter_002', 'Interpreter', 'No', 1, 1);
INSERT INTO performance_admin(id, code, type, value, status, sequence) VALUES (6, 'Stat_001', 'Status', 'Unresolved', 1, 1);
INSERT INTO performance_admin(id, code, type, value, status, sequence) VALUES (7, 'Stat_002', 'Status', 'Resolved', 1, 2);

INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (1, 'OAD', 'Alcohol and other drugs policy', 1, 1, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (2, 'SC_046', 'General breach of Alcohol and other Drugs policy', 1, 2, 1);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (3, 'SC_047', 'Refusal to participate in Alcohol and/or drug test', 1, 3, 1);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (4, 'OAW', 'Animal welfare Policy', 1, 4, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (5, 'SC_052', 'Animal welfare Policy', 1, 5, 4);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (6, 'OCC', 'Code of conduct', 1, 6, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (7, 'SC_044', 'Code of conduct', 1, 7, 6);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (8, 'OCP', 'Communication Policy', 1, 8, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (9, 'SC_001', 'General breach of Communication Policy', 1, 9, 8);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (10, 'SC_002', 'Downloading and/or distributing inappropriate material', 1, 10, 8);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (11, 'SC_003', 'Inappropriate use of Company IT equipment', 1, 11, 8);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (12, 'CP', 'Complaints Policy', 1, 12, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (13, 'SC_055', 'General breach of complaints policy', 1, 13, 12);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (14, 'SC_056', 'Making false, frivolous or vexatious complaints', 1, 14, 12);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (15, 'SC_057', 'Breach of confidentiality', 1, 15, 12);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (16, 'OCI', 'Contact of interest', 1, 16, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (17, 'SC_045', 'Conflict of interest', 1, 17, 16);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (18, 'OHP', 'Discrimination, Harassment, Bullying and Victimisation Policy', 1, 18, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (19, 'SC_037', 'General breach of Discrimination, Harassment, Bullying and Victimisation Policy', 1, 19, 18);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (20, 'SC_038', 'Physical Violence', 1, 20, 18);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (21, 'SC_039', 'Sexual harassment', 1, 21, 18);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (22, 'SC_040', 'Bullying', 1, 22, 18);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (23, 'SC_041', 'Harassment', 1, 23, 18);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (24, 'SC_042', 'Victimisation', 1, 24, 18);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (25, 'OEC', 'Employment Contract', 1, 25, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (26, 'SC_028', 'Confidential information', 1, 26, 25);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (27, 'OEP', 'Environmental Policy', 1, 27, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (28, 'SC_027', 'Environmental Policy', 1, 28, 27);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (29, 'OFS', 'Food Safety & Quality Assurance Policy', 1, 29, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (30, 'SC_004', 'General breach of Food Safety Policy', 1, 30, 29);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (31, 'SC_005', 'Allergens in processing area', 1, 31, 29);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (32, 'SC_006', 'Throwing Product', 1, 32, 29);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (33, 'SC_007', 'Breach of Dropped Meat Procedure', 1, 33, 29);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (34, 'SC_008', 'Loose items in production area', 1, 34, 29);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (35, 'SC_009', 'Smoking where not permitted', 1, 35, 29);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (36, 'SC_010', 'Food and Drink where not permitted', 1, 36, 29);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (37, 'SC_011', 'Failure to follow reasonable work instructions', 1, 37, 29);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (38, 'SC_012', 'Failure to put soiled clothing in allocated bins', 1, 38, 29);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (39, 'SC_013', 'Breach of site based jewellery or cosmetics policy', 1, 39, 29);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (40, 'SC_014', 'Failure to keep lockers in hygienic state', 1, 40, 29);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (41, 'OID', 'ID card policy', 1, 41, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (42, 'SC_048', 'General breach of ID card Policy', 1, 42, 41);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (43, 'SC_049', 'Failure to use kronos clocks', 1, 43, 41);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (44, 'SC_050', 'Lose of kronos card - ongoing', 1, 44, 41);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (45, 'SC_051', 'Misuse of kronos cardn', 1, 45, 41);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (46, 'LDP', 'Learning & Development Policy', 1, 46, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (47, 'SC_017', 'General breach of Learning & Development Policy', 1, 47, 46);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (48, 'SC_018', 'Performing Task without Task Description', 1, 48, 46);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (49, 'SC_019', 'Failure to follow reasonable instructions', 1, 49, 46);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (50, 'OPP', 'Privacy Policy', 1, 50, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (51, 'SC_015', 'General breach of the privacy policy', 1, 51, 50);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (52, 'SC_016', 'Breach of confidentiality', 1, 52, 50);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (53, 'OST', 'Standard Terms and Conditions of Employment', 1, 53, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (54, 'SC_029', 'General breach of ST&C', 1, 54, 53);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (55, 'SC_030', 'Unauthorised Absence', 1, 12, 53);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (56, 'SC_031', 'Damage to company property', 1, 56, 53);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (57, 'SC_032', 'Damage to Company reputation', 1, 57, 53);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (58, 'SC_033', 'Theft from Company or other employee', 1, 58, 53);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (59, 'SC_034', 'Failure to permit bag/locker search to be conducted', 1, 59, 53);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (60, 'SC_035', 'Deliberate insubordination', 1, 60, 53);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (61, 'SC_036', 'Breach of cardinal rule', 1, 61, 53);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (62, 'OTP', 'Trade practices', 1, 62, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (63, 'SC_043', 'Trade practices Policy', 1, 63, 62);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (64, 'OWS', 'Workplace Health and Safety Policy', 1, 64, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (65, 'SC_020', 'General breach of WH&S Policy ', 1, 65, 64);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (66, 'SC_021', 'Breach involving PPE', 1, 66, 64);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (67, 'SC_022', 'Breach of policy involving knives ', 1, 67, 64);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (68, 'SC_023', 'Vehicle operation - including without licence/authorisation/recklessly', 1, 68, 64);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (69, 'SC_024', 'Failure to wear hearing PPE or wearing radio headsets', 1, 69, 64);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (70, 'SC_025', 'Failure to follow protocols when working around livestock', 1, 70, 64);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (71, 'SC_026', 'Failure to report injuries/incidents', 1, 71, 64);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (72, 'OWR', 'Workplace Rehabilitation Policy', 1, 72, null);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (73, 'SC_053', 'General breach of Workplace Rehab Policy', 1, 73, 72);
INSERT INTO performance_category(id, code, name, status, sequence, parent_category_id) VALUES (74, 'SC_054', 'Failure to comply with RTW plan', 1, 74, 72);

INSERT INTO performance_action(id, code, name, status) VALUES (1, 'AB', 'Abandonment of employment', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (2, 'FA', 'Failure to notify of Absence', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (3, 'RW', 'Re-confirmation of final', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (4, 'FR', 'Supervisor report', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (5, 'CS', 'Counselling session', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (6, 'WC', 'Written counselling', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (7, 'SU', 'Suspension', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (8, 'NS', 'Notice of suspension', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (9, 'WW', '1st warning', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (10, 'WS', '2nd warning', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (11, 'FW', 'Final warning', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (12, 'AM', 'Attendance monitoring', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (13, 'WR', 'Written response', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (14, 'SA', 'Stood Aside', 1);
INSERT INTO performance_action(id, code, name, status) VALUES (15, 'OT', 'Other', 1);

INSERT INTO letter_template(id, code, name, status, template_file) VALUES (1,'001','Abandonment of employement letter 1',1,'Abandonment letter 1.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (2,'002','Abandonment of employement letter 2',1,'Abandonment letter 2.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (3,'003','Abandonment of employement letter 3',1,'Abandonment letter 3.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (4,'004','Failure to notify of Absence (1)',1,'Failure to Notify of Absence 1.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (5,'005','Failure to notify of Absence (2)',1,'Failure to Notify of Absence 2.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (6,'006','Failure to notify of Absence (3)',1,'Failure to Notify of Absence 3.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (7,'007','Re-confirmation of Final Written Warning',1,'Re-Confirmation of Final Written Warning.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (8,'008','Written Counselling',1,'Written Counselling.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (9,'009','Suspension in lieu of termination (Final WW)',1,'Suspension in lieu of termination (Final WW).docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (10,'010','Notice of suspension',1,'Notice of Suspension.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (11,'011','1st Warning',1,'First Written Warning.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (12,'012','2nd Warning',1,'Second Written Warning.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (13,'013','Final Warning',1,'Final Written Warning.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (14,'014','Attendance Monitoring Program',1,'Attendance Monitoring Program.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (15,'015','Letter of Response (Misconduct)',1,'Letter of Response (Misconduct).docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (16,'016','Stood aside pending investigation',1,'Stood aside pending investigation.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (17,'017','Unauthorised absenteeism (1)',1,'Unauthorised Absenteeism 1.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (18,'018','Unauthorised absenteeism (2)',1,'Unauthorised Absenteeism 2.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (19,'019','Unauthorised absenteeism (3)',1,'Unauthorised Absenteeism 3.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (20,'020','Termination (Probation)',1,'Termination (Probation).docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (21,'021','Offer of support person',1,'Offer of Support Person.pdf');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (22,'022','Demand for Return of property',1,'Demand to return property.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (23,'023','Employee Exit Checklist',1,'Employee Exit Checklist.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (24,'024','New Employee Assessment',1,'New employee assessment.docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (25,'025','Letter of Response (Work Performance)',1,'Letter of Response (Work Performance).docx');
INSERT INTO letter_template(id, code, name, status, template_file) VALUES (26,'026','Termination (Misconduct)',1,'Termination (Misconduct).docx');

INSERT INTO department(id, code, name) VALUES (1,'department_1', 'ADELAIDE');
INSERT INTO department(id, code, name) VALUES (2,'department_2', 'BEEF BUYERS');
INSERT INTO department(id, code, name) VALUES (3,'department_3', 'BEEF CITY');
INSERT INTO department(id, code, name) VALUES (4,'department_4', 'BORDERTOWN');
INSERT INTO department(id, code, name) VALUES (5,'department_5', 'BRISBANE');
INSERT INTO department(id, code, name) VALUES (6,'department_6', 'BROOKLYN');
INSERT INTO department(id, code, name) VALUES (7,'department_7', 'CHULORA');
INSERT INTO department(id, code, name) VALUES (8,'department_8', 'COBRAM');
INSERT INTO department(id, code, name) VALUES (9,'department_9', 'DEVELOPMENT');
INSERT INTO department(id, code, name) VALUES (10,'department_10', 'DEVONPORT');
INSERT INTO department(id, code, name) VALUES (11,'department_11', 'DINMORE');
INSERT INTO department(id, code, name) VALUES (12,'department_12', 'FOODPARTNERS');
INSERT INTO department(id, code, name) VALUES (13,'department_13', 'HELP DESK');
INSERT INTO department(id, code, name) VALUES (14,'department_14', 'HR SYSTEMS');
INSERT INTO department(id, code, name) VALUES (15,'department_15', 'INFRASTRUCTURE');
INSERT INTO department(id, code, name) VALUES (16,'department_16', 'INTERNAL AUDIT');
INSERT INTO department(id, code, name) VALUES (17,'department_17', 'JOES SITES');
INSERT INTO department(id, code, name) VALUES (18,'department_18', 'LEGAL');
INSERT INTO department(id, code, name) VALUES (19,'department_19', 'LONGFORD');
INSERT INTO department(id, code, name) VALUES (20,'department_20', 'MAN ACCOUNTING');
INSERT INTO department(id, code, name) VALUES (21,'department_21', 'MELBOURNE');
INSERT INTO department(id, code, name) VALUES (22,'department_22', 'MUGINDI');
INSERT INTO department(id, code, name) VALUES (23,'department_23', 'OHS');
INSERT INTO department(id, code, name) VALUES (24,'department_24', 'OPS AND PROCESSES');
INSERT INTO department(id, code, name) VALUES (25,'department_25', 'PAYROLL');
INSERT INTO department(id, code, name) VALUES (26,'department_26', 'PERTH');
INSERT INTO department(id, code, name) VALUES (27,'department_27', 'PORT WAKEFIELD');
INSERT INTO department(id, code, name) VALUES (28,'department_28', 'PRIME CITY');
INSERT INTO department(id, code, name) VALUES (29,'department_29', 'PRODUCTION STREET');
INSERT INTO department(id, code, name) VALUES (30,'department_30', 'REG AND PUBLIC AFFAIRS');
INSERT INTO department(id, code, name) VALUES (31,'department_31', 'RIVERINA');
INSERT INTO department(id, code, name) VALUES (32,'department_32', 'ROCKHAMPTON');
INSERT INTO department(id, code, name) VALUES (33,'department_33', 'SCONE');
INSERT INTO department(id, code, name) VALUES (34,'department_34', 'SHEEP BUYERS');
INSERT INTO department(id, code, name) VALUES (35,'department_35', 'SHIPPING');
INSERT INTO department(id, code, name) VALUES (36,'department_36', 'SYDNEY');
INSERT INTO department(id, code, name) VALUES (37,'department_37', 'TASMANIA');
INSERT INTO department(id, code, name) VALUES (38,'department_38', 'TOWNSVILLE');
INSERT INTO department(id, code, name) VALUES (39,'department_39', 'TRADING');
INSERT INTO department(id, code, name) VALUES (40,'department_40', 'TRAINING');
INSERT INTO department(id, code, name) VALUES (41,'department_41', 'TREASURY');
INSERT INTO department(id, code, name) VALUES (42,'department_42', 'WACOL');
INSERT INTO department(id, code, name) VALUES (43,'department_43', 'WORKERS COMP');

INSERT INTO section(id, code, name) VALUES (1,'section_1', 'BACON');
INSERT INTO section(id, code, name) VALUES (2,'section_2', 'BONING ROOM');
INSERT INTO section(id, code, name) VALUES (3,'section_3', 'BONING ROOM - BEEF');
INSERT INTO section(id, code, name) VALUES (4,'section_4', 'BONING ROOM - SHEEP');
INSERT INTO section(id, code, name) VALUES (5,'section_5', 'COLD STORES');
INSERT INTO section(id, code, name) VALUES (6,'section_6', 'ENGINEERING OPERATIONS');
INSERT INTO section(id, code, name) VALUES (7,'section_7', 'ENVIRONMENT');
INSERT INTO section(id, code, name) VALUES (8,'section_8', 'FINANCE & ADMINISTRATION');
INSERT INTO section(id, code, name) VALUES (9,'section_9', 'FSQA');
INSERT INTO section(id, code, name) VALUES (10,'section_10', 'HAM');
INSERT INTO section(id, code, name) VALUES (11,'section_11', 'HIDES');
INSERT INTO section(id, code, name) VALUES (12,'section_12', 'HR OPERATIONS');
INSERT INTO section(id, code, name) VALUES (13,'section_13', 'KILL FLOOR');
INSERT INTO section(id, code, name) VALUES (14,'section_14', 'KILL FLOOR - BEEF');
INSERT INTO section(id, code, name) VALUES (15,'section_15', 'KILL FLOOR - SHEEP');
INSERT INTO section(id, code, name) VALUES (16,'section_16', 'LOADOUT');
INSERT INTO section(id, code, name) VALUES (17,'section_17', 'OFFAL');
INSERT INTO section(id, code, name) VALUES (18,'section_18', 'PRODUCTION CONTROL');
INSERT INTO section(id, code, name) VALUES (19,'section_19', 'PURCHASING');
INSERT INTO section(id, code, name) VALUES (20,'section_20', 'RENDERING');
INSERT INTO section(id, code, name) VALUES (21,'section_21', 'SAFETY');
INSERT INTO section(id, code, name) VALUES (22,'section_22', 'SALAMI');
INSERT INTO section(id, code, name) VALUES (23,'section_23', 'SALES');
INSERT INTO section(id, code, name) VALUES (24,'section_24', 'TRANSPORT & LOGISTICS');
INSERT INTO section(id, code, name) VALUES (25,'section_25', 'VALUE ADDED');
INSERT INTO section(id, code, name) VALUES (26,'section_26', 'WAREHOUSE & DISTRIBUTION');

INSERT INTO shift(id, code, name) VALUES (1,'shift_1', 'Day Shift');
INSERT INTO shift(id, code, name) VALUES (2,'shift_2', 'Afternoon Shift');
INSERT INTO shift(id, code, name) VALUES (3,'shift_3', 'Night Shift');

INSERT INTO position(id, code, name) VALUES (1,'position_1', 'Packing Loin Lvl7');
INSERT INTO position(id, code, name) VALUES (2,'position_2', 'Analyst Programmer (Net)');
INSERT INTO position(id, code, name) VALUES (3,'position_3', 'Packing Legs Lvl7');
INSERT INTO position(id, code, name) VALUES (4,'position_4', 'Trades Assistant Maint');
INSERT INTO position(id, code, name) VALUES (5,'position_5', 'Training Officer HR');
INSERT INTO position(id, code, name) VALUES (6,'position_6', 'Training Lvl7');
INSERT INTO position(id, code, name) VALUES (7,'position_7', 'Cryoval Operator Lvl7');
INSERT INTO position(id, code, name) VALUES (8,'position_8', 'Engineering Supervisor Bacon Packaging');
INSERT INTO position(id, code, name) VALUES (9,'position_9', 'Rejects Lvl7');
INSERT INTO position(id, code, name) VALUES (10,'position_10', 'Engineering Supervisor Salami/Slice Pack');

INSERT INTO site(id, code, name, status) VALUES (50001,'site-1', 'Beef City', 1);

INSERT INTO site(id, code, name, status) VALUES (1,'ANM01', 'Andrews Meat', 1);
INSERT INTO site(id, code, name, status) VALUES (2,'BCF01', 'JBS Beef City Feedlot', 1);
INSERT INTO site(id, code, name, status) VALUES (3,'BCP01', 'JBS Beef City Plant', 1);
INSERT INTO site(id, code, name, status) VALUES (4,'BCS01', 'Bremer Cold Store', 1);
INSERT INTO site(id, code, name, status) VALUES (5,'BOP01', 'JBS Bordertown Plant', 1);
INSERT INTO site(id, code, name, status) VALUES (6,'BRP01', 'JBS Brooklyn Plant', 1);
INSERT INTO site(id, code, name, status) VALUES (7,'CFL01', 'Caroona Feedlot', 1);
INSERT INTO site(id, code, name, status) VALUES (8,'COO01', 'JBS Corporate Office', 1);
INSERT INTO site(id, code, name, status) VALUES (9,'COP01', 'JBS Cobram Plant', 1);
INSERT INTO site(id, code, name, status) VALUES (10,'CTL01', 'JBS Carriers (Transport & Logistics)', 1);
INSERT INTO site(id, code, name, status) VALUES (11,'DEP01', 'JBS Devonport Plant', 1);
INSERT INTO site(id, code, name, status) VALUES (12,'DIP01', 'JBS Dinmore Plant', 1);
INSERT INTO site(id, code, name, status) VALUES (13,'DRJ01', 'D.R. Johnston', 1);
INSERT INTO site(id, code, name, status) VALUES (14,'HCS01', 'Hemmant Cold Store', 1);
INSERT INTO site(id, code, name, status) VALUES (15,'KNI01', 'Knox International', 1);
INSERT INTO site(id, code, name, status) VALUES (16,'LIB01', 'Livestock Buyer', 1);
INSERT INTO site(id, code, name, status) VALUES (17,'LOP01', 'JBS Longford Plant', 1);
INSERT INTO site(id, code, name, status) VALUES (18,'MUF01', 'Mungindi Feedlot', 1);
INSERT INTO site(id, code, name, status) VALUES (19,'PCF01', 'Prime City Feedlot', 1);
INSERT INTO site(id, code, name, status) VALUES (20,'PRC01', 'Primo - NSW Corporate Office', 1);
INSERT INTO site(id, code, name, status) VALUES (21,'PRF01', 'Primo - Foodpartners', 1);
INSERT INTO site(id, code, name, status) VALUES (22,'RBS01', 'Retail Butcher Shop', 1);
INSERT INTO site(id, code, name, status) VALUES (23,'RIF01', 'JBS Riverina Feedlot', 1);
INSERT INTO site(id, code, name, status) VALUES (24,'RIP01', 'JBS Riverina Plant', 1);
INSERT INTO site(id, code, name, status) VALUES (25,'ROP01', 'JBS Rockhampton Plant', 1);
INSERT INTO site(id, code, name, status) VALUES (26,'SCP01', 'JBS Scone Plant', 1);
INSERT INTO site(id, code, name, status) VALUES (27,'SOO01', 'JBS Southern Operations Office', 1);
INSERT INTO site(id, code, name, status) VALUES (28,'STB01', 'Swift Trade Group - Byproducts', 1);
INSERT INTO site(id, code, name, status) VALUES (29,'STM01', 'JBS Townsville Plant', 1);
INSERT INTO site(id, code, name, status) VALUES (30,'TOP01', 'Andrews Meat', 1);
INSERT INTO site(id, code, name, status) VALUES (31,'YAF01', 'Yambinya Feedlott', 1);

/*INSERT INTO employee(id, employee_number, first_name, last_name, full_name, position_id, department_id, section_id, shift_id, site_id, plant_id) VALUES (1,'230945','Derrick','Stewart','Derrick Stewart',1,1,1,1,50001,1);
INSERT INTO employee(id, employee_number, first_name, last_name, full_name, position_id, department_id, section_id, shift_id, site_id, plant_id) VALUES (2,'610700','Alison','Bennett','Alison Bennett',2,2,2,2,50001,2);
INSERT INTO employee(id, employee_number, first_name, last_name, full_name, position_id, department_id, section_id, shift_id, site_id, plant_id) VALUES (3,'61368','Janice','Ferguson','Janice Ferguson',3,3,3,3,50001,3);
INSERT INTO employee(id, employee_number, first_name, last_name, full_name, position_id, department_id, section_id, shift_id, site_id, plant_id) VALUES (4,'231605','Sammy','Day','Sammy Day',4,4,4,1,50001,4);
INSERT INTO employee(id, employee_number, first_name, last_name, full_name, position_id, department_id, section_id, shift_id, site_id, plant_id) VALUES (5,'300110','Clifton','Ball','Clifton Ball',5,5,5,2,50001,5);
INSERT INTO employee(id, employee_number, first_name, last_name, full_name, position_id, department_id, section_id, shift_id, site_id, plant_id) VALUES (6,'230687','Brian','Huff','Brian Huff',6,6,6,3,50001,6);
INSERT INTO employee(id, employee_number, first_name, last_name, full_name, position_id, department_id, section_id, shift_id, site_id, plant_id) VALUES (7,'1001575','Josh','Newman','Josh Newman',7,7,7,1,50001,7);
INSERT INTO employee(id, employee_number, first_name, last_name, full_name, position_id, department_id, section_id, shift_id, site_id, plant_id) VALUES (8,'212812','Bradford','Montgomery','Bradford Montgomery',8,8,8,2,50001,8);
INSERT INTO employee(id, employee_number, first_name, last_name, full_name, position_id, department_id, section_id, shift_id, site_id, plant_id) VALUES (9,'300444','Rosa','Baldwin','Rosa Baldwin',9,9,9,3,50001,9);
INSERT INTO employee(id, employee_number, first_name, last_name, full_name, position_id, department_id, section_id, shift_id, site_id, plant_id) VALUES (10,'230935','Kurt','Grave','Kurt Grave',10,10,10,1,50001,10);

INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (1,1,'FW',1);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (2,2,'AM',1);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (3,3,'WR',1);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (4,4,'SA',1);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (5,5,'AM',1);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (6,6,'FW',1);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (7,7,'SA',1);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (8,8,'WR',1);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (9,9,'SR',1);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (10,10,'FW',1);

INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (11,1,'AC',2);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (12,2,'AB',2);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (13,3,'AB',2);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (14,4,'AA',2);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (15,5,'UA',2);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (16,6,'AC',2);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (17,7,'AB',2);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (18,8,'UA',2);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (19,9,'AB',2);
INSERT INTO employee_event(id, employee_id, event_name, event_type) VALUES (20,10,'AA',2);*/
