package unifal.hotel.book;

public class HotelBook {
    public final static String CREATE_TABLE_PERSON =
            // language=SQL
            "CREATE TABLE person (" +
                    "id BIGINT(11) NOT NULL UNIQUE," +
                    "name VARCHAR(255) NOT NULL, " +
                    "surname VARCHAR(255) NOT NULL, " +
                    "cellphone BIGINT(11) UNIQUE," +
                    "birthdate DATE NOT NULL," +
                    "gender VARCHAR(1) NOT NULL," +
                    "PRIMARY KEY (id)" +
                    ");";

    public final static String CREATE_TABLE_ADDRESS =
            // language=SQL
            "CREATE TABLE address (" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE, " +
                    "person_id BIGINT(11) NOT NULL," +
                    "street VARCHAR(255) NOT NULL," +
                    "neighborhood VARCHAR(255) NOT NULL," +
                    "zipcode BIGINT(255) NOT NULL," +
                    "city VARCHAR(255) NOT NULL, " +
                    "state VARCHAR(255) NOT NULL," + 
                    "country VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (id), " +
                    "FOREIGN KEY (person_id) REFERENCES person(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_ACCOUNT =
            // language=SQL
            "CREATE TABLE account(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "person_id BIGINT(11) NOT NULL UNIQUE," +
                    "email VARCHAR(255) NOT NULL UNIQUE," +
                    "password VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_CLIENT =
            // language=SQL
            "CREATE TABLE client(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "person_id BIGINT(11) NOT NULL UNIQUE," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (person_id) REFERENCES person(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_EMPLOYEE =
            //language=SQL
            "CREATE TABLE employee( " +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "person_id BIGINT(11) NOT NULL UNIQUE," +
                    "hire_date DATE NOT NULL," +
                    "salary INT NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (person_id) REFERENCES person(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_MANAGER =
            //language=SQL
            "CREATE TABLE manager(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE, " +
                    "employee_id INT NOT NULL UNIQUE," +
                    "PRIMARY KEY (id), " +
                    "FOREIGN KEY (employee_id) REFERENCES employee(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String CREATE_TABLE_RECEPTIONIST =
            //language=SQL
            "CREATE TABLE receptionist(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "employee_id INT NOT NULL UNIQUE," +
                    "rating INT NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (employee_id) REFERENCES employee(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ")";

    public final static String CREATE_TABLE_ROOM =
            // language=SQL
            "CREATE TABLE room(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "type VARCHAR(1) NOT NULL," +
                    "rent INT NOT NULL," +
                    "PRIMARY KEY (id)" +
                    ");";

    public final static String CREATE_TABLE_BOOKING =
            // language=SQL
            "CREATE TABLE booking(" +
                    "id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "client_id INT NOT NULL," +
                    "room_id INT NOT NULL," +
                    "checkin_date DATE NOT NULL," +
                    "checkout_date DATE NOT NULL, " +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (client_id) REFERENCES client(id) ON UPDATE CASCADE ON DELETE CASCADE, " +
                    "FOREIGN KEY (room_id) REFERENCES  room(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

    public final static String DROP_ALL_TABLES =
            // language=SQL
            "DROP TABLE receptionist, address, booking, account, client, manager, employee, room, person";

    public final static String INSERT_NEW_PEOPLE =
            // language=SQL
            "INSERT INTO person(id, name, surname, cellphone, birthdate, gender) VALUES (?, ?, ?, ?, ?, ?);";

    public final static String INSERT_NEW_ACCOUNT =
            //language=SQL
            "INSERT INTO account(id, person_id, email, password) VALUES (NULL, ?, ?, ?);";

    public final static String INSERT_NEW_ADDRESS =
            //language=SQL
            "INSERT INTO address(id, person_id, street, neighborhood, zipcode, city, country, state) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?);";

    public final static String INSERT_NEW_CLIENT =
            //language=SQL
            "INSERT INTO client(id, person_id) VALUES (NULL, ?);";

    public final static String INSERT_NEW_EMPLOYEE =
            //language=SQL
            "INSERT INTO employee(id, person_id, hire_date, salary) VALUES (NULL, ?, ?, ?);";

    public final static String INSERT_NEW_RECEPTIONIST =
            //language=SQL
            "INSERT INTO receptionist(id, employee_id, rating) VALUES (NULL, ?, 0)";

    public final static String INSERT_NEW_MANAGER =
            //language=SQL
            "INSERT INTO manager(id, employee_id) VALUES (NULL, ?);";

    public final static String SELECT_ALL_MANAGERS_ID =
            //language=SQL
            "SELECT employee_id FROM manager;";

    public final static String SELECT_ALL_RECEPTIONIST_ID =
            //language=SQL
            "SELECT employee_id FROM receptionist;";

    public final static String INSERT_NEW_ROOM =
            //language=SQL
            "INSERT INTO room(id, type, rent) VALUES (NULL, ?, ?);";

    public final static String INSERT_NEW_BOOKING =
            //language=SQL
            "INSERT INTO booking(id, client_id, room_id, checkin_date, checkout_date) VALUES (NULL, ?, ?, ?, ?)";

    public final static String SELECT_ALL_BOOKINGS_BETWEEN_TWO_DATES =
            //language=SQL
            "SELECT * FROM booking WHERE room_id = ? AND NOT (checkout_date <= ? OR checkin_date >= ?);";


    /**
     * Início da utilização de query personalizadas para utilização no JPA e Jakarta
     */
    public final static String CHECK_IF_EXIST_BOOKING_CONFLICTS =
            //language=SQL
            "SELECT COUNT(b.id) > 0 FROM Booking b WHERE b.room_id = :roomId AND NOT (b.checkout_date <= :startDate OR b.checkin_date >= :endDate)";


    /**
     *  Início da população do banco de dados
     */

    public final static String POPULATE_PERSONS_FIRST =
            //language=SQL
            """
                INSERT INTO person(id,  name, surname, cellphone, birthdate, gender) VALUES ('87765886886','Thales','Murilo','85981413119','2000-05-02','M'), ('44529183955','Rafael','Ryan','68985492380','2004-07-21','M'), ('25621234766','Ester','Louise','96983224271','1991-07-02','F'), ('67212112038','Lorenzo','Elias','95981869779','1974-09-24','M'), ('34046725427','Levi','Roberto','48982929016','1992-09-14','M'), ('92835730648','Teresinha','Camila','63988597707','1993-01-04','F'), ('15784147170','Manuela','Louise','96991684165','1975-07-16','F'), ('38699500130','Kamilly','Priscila','95992054818','1968-04-17','F'), ('82264902353','Luciana','Marli','98983179059','1980-02-13','F'), ('12853868176','Priscila','Bianca','47994061218','1948-02-10','F'), ('58321829953','Gabrielly','Camila','54983518281','2000-08-24','F'), ('13983211682','Rafael','Kauê','67981555355','1970-10-20','M'), ('86530197311','Mário','Vitor','91995105009','1956-09-18','M'), ('14996733020','Laís','Liz','21992006332','1979-11-08','F'), ('86827123855','Sebastião','Nathan','98992902462','1968-01-11','M'), ('16655806585','Mário','Samuel','85983599631','1964-07-26','M'), ('16450680482','Bernardo','Rafael','96987117864','1952-02-15','M'), ('72514651247','Stella','Pietra','71997657869','2001-05-20','F'), ('89194779228','Benício','Miguel','95995202388','1997-08-10','M'), ('35535124150','Mariah','Giovanna','68988497870','1966-05-05','F'), ('86239109665','Elias','Thales','84993414665','1971-09-11','M'), ('81905215436','Fátima','Fernanda','41987310107','2000-10-19','F'), ('29601039201','Vera','Ayla','34983795702','1957-11-21','F'), ('28126974150','Paulo','Diogo','82981624562','1943-11-05','M'), ('44491227390','Raimunda','Carolina','63985782520','1953-09-18','F'), ('83991436000','Eduarda','Larissa','91996315214','2003-06-18','F'), ('60540842168','Ester','Carolina','69991624647','1961-01-18','F'), ('13646455055','Fernanda','Heloise','91981480092','1947-11-22','F'), ('22151889440','José','Filipe','19989884868','2003-07-13','M'), ('65273833205','Ricardo','Márcio','67995714479','1957-08-19','M'), ('98743612806','Kevin','Gustavo','68982445770','1964-02-21','M'), ('15701660648','Roberto','Leandro','67986464482','1996-03-02','M'), ('67250736620','Gael','Oliver','91981979442','1968-09-19','M'), ('24810974677','Larissa','Pietra','98982597993','1991-05-22','F'), ('51265240973','Cauã','Pedro','86987126063','1956-10-21','M'), ('17244444680','Emanuelly','Andreia','68985369965','1949-05-20','F'), ('74016794692','Ayla','Josefa','67997941831','1996-08-12','F'), ('61415685215','Emilly','Brenda','82987888433','1978-01-27','F'), ('17341005408','Lucas','Igor','82989027840','1979-10-06','M'), ('57224073644','Heloise','Agatha','21988418511','1982-04-03','F'), ('84251378105','Andreia','Ayla','92984296318','1973-04-20','F'), ('99615402630','Luiz','Rafael','44984200626','1987-07-03','M'), ('11678337838','Rebeca','Isabela','79988344565','1970-02-23','F'), ('42851808478','Sophie','Helena','95987197970','1974-01-10','F'), ('84046999179','Ester','Sônia','21984015191','1966-10-22','F'), ('52035968160','Antonio','Kevin','84992218370','1987-01-05','M'), ('25629413732','Gabriela','Camila','67981374282','1989-01-27','F'), ('57387916306','Luís','Anderson','63994629935','1989-07-06','M'), ('18661319404','Anthony','Iago','79984577771','1953-04-26','M'), ('64246098906','Geraldo','Kaique','62983993864','1993-07-08','M'), ('97887578043','Theo','Bernardo','82987094069','1971-05-11','M'), ('63058896786','Oliver','Heitor','61991447988','1975-09-14','M'), ('13494405158','Márcio','Alexandre','96999361659','1980-10-21','M'), ('91993281860','Cauê','Caleb','95999810920','2001-03-03','M'), ('24034278617','Oliver','Carlos','83995435760','1999-09-10','M'), ('74067195230','Rosângela','Valentina','61998819719','1978-11-07','F'), ('71666063983','Sônia','Alana','49995433804','1994-10-11','F'), ('27017359984','Cláudia','Aurora','49983025253','1953-08-21','F'), ('47061179686','Raquel','Isadora','92993150593','1973-11-17','F'), ('40175271240','Heloise','Malu','84998827272','1979-08-02','F'), ('93929547198','Aline','Benedita','89989823926','1952-07-26','F'), ('26306208674','Thomas','Lucca','86991644902','1955-11-06','M'), ('71097040259','Vitor','Carlos','44985780394','1998-04-22','M'), ('98753661109','Francisca','Isabela','82991350598','1945-08-09','F'), ('81327223295','Lucas','Elias','11999950033','1970-04-11','M'), ('61379060940','Ian','Hugo','61988013261','1975-06-11','M'), ('38134782892','Levi','José','83993674902','1963-09-24','M'), ('75601782784','Daiane','Louise','61991045283','1988-04-16','F'), ('91491728248','Maya','Amanda','91998558399','1953-04-18','F'), ('94586048263','Thiago','Benício','66992305943','1981-06-27','M'), ('55418294539','Luna','Maitê','53991451587','1947-02-25','F'), ('37453307813','Breno','Gabriel','27999183335','1953-07-03','M'), ('25834149413','Kaique','Luís','31982567345','1999-08-20','M'), ('86909920490','Natália','Carolina','48997465539','1997-07-11','F'), ('55333247471','Diego','Kevin','21989152513','1997-03-25','M'), ('58868707098','Alana','Pietra','98989882194','1960-05-10','F'), ('90521414954','Noah','Luís','61998237422','2004-11-16','M'), ('73747957420','Thiago','Renato','61984276225','1951-10-27','M'), ('45308289484','Esther','Betina','47991997045','1967-11-19','F'), ('11668967146','Ryan','Diogo','42998388909','1975-11-17','M'), ('49707409339','Isis','Nina','93983857586','1968-03-02','F'), ('34616010803','Alexandre','Elias','79994731199','1983-01-25','M'), ('94771833508','Teresinha','Lavínia','96993246717','1981-11-21','F'), ('63680610734','Elias','Joaquim','82981453354','1949-09-12','M'), ('19538847687','Gabrielly','Isabella','95985150332','1997-09-24','F'), ('26280885259','Fábio','José','86998359621','1991-05-08','M'), ('10480493537','Lara','Malu','95985322052','1982-05-01','F'), ('76545416510','Sophie','Stella','68993877082','1979-04-27','F'), ('21805648560','Silvana','Aparecida','84992355726','1962-11-16','F'), ('30570955858','Alice','Isabelly','51996496439','1945-05-03','F'), ('16882754527','Fabiana','Marli','82991011927','1953-06-20','F'), ('70406233314','Yuri','Leonardo','82986756803','1963-08-13','M'), ('14471885863','Emily','Lavínia','63995676249','1949-02-13','F'), ('78103002453','Pedro','Henrique','96995238351','1972-01-06','M'), ('66955111019','Lavínia','Marcela','98996642034','1986-11-16','F'), ('24333308402','Carla','Joana','96991858953','1948-02-25','F'), ('85248085519','Márcio','Severino','86983772121','1983-11-13','M'), ('35907964138','Renato','Caleb','21998651307','1984-03-25','M'), ('34772047603','Elza','Daniela','95995025725','1951-05-16','F'), ('54498235258','Larissa','Sueli','63995663619','1998-04-15','F'), ('90011722100','Edson','João','98997888629','1973-09-11','M'), ('87700455341','Manuel','Diogo','95987682402','1943-04-18','M'), ('17250158201','Fátima','Mariane','81987766728','1989-09-17','F'), ('57628365507','Patrícia','Analu','73988741902','1998-09-24','F'), ('57986377607','Brenda','Elaine','86988739056','1982-05-13','F'), ('98362431784','Matheus','Benício','12981246292','2001-06-14','M'), ('98355998154','Marcos','Benício','67993545462','1967-09-17','M'), ('22808254903','Iago','Noah','27981746489','1969-03-05','M'), ('47193695100','Luís','Edson','61997636575','1993-02-12','M'), ('60151968314','Rebeca','Giovanna','92988086774','1990-08-13','F'), ('69641497243','Simone','Heloisa','67993389807','1958-07-18','F'), ('41777136431','Geraldo','Levi','84989339032','1961-10-21','M'), ('61106395247','Carla','Laura','98987897497','1997-03-16','F'), ('44880142085','Diego','Gabriel','81997880641','1986-04-15','M'), ('74635547752','Antonella','Isabelly','47983872338','1968-02-13','F'), ('22071534603','Roberto','Iago','95983242409','1960-08-02','M'), ('58364875612','Flávia','Nina','41984989908','1966-10-12','F'), ('18473808088','Mirella','Aurora','63981389062','1965-08-20','F'), ('28446011700','Marcelo','Pedro','82989128095','1972-01-06','M'), ('86937396708','Mirella','Manuela','69998891468','1979-04-21','F'), ('53671283947','Gustavo','Kaique','61983869679','2003-04-01','M'), ('44054945686','Miguel','José','75986249533','1964-08-09','M'), ('26286833501','Antonella','Raquel','83998847533','1971-02-21','F'), ('65988418520','Nelson','Calebe','95989551703','1968-05-16','M'), ('43737958432','Alice','Isabela','65991191945','1966-05-24','F'), ('33805413629','Milena','Evelyn','51999961190','1965-06-07','F'), ('87734831508','Sandra','Vitória','92983757172','1965-07-08','F'), ('58075672852','Tatiane','Daniela','82991738700','2005-08-12','F'), ('52261999887','Luiza','Milena','81999430694','1966-04-27','F'), ('83304603204','Felipe','Giovanni','21985081490','2002-03-13','M'), ('68996889210','Valentina','Agatha','21999084955','1963-06-07','F'), ('33162273340','Ricardo','Carlos','68995740746','1976-03-05','M');
            """;

    public final static String POPULATE_PERSONS_SECOND =
            //language=SQL
            """
                INSERT INTO person(id,  name, surname, cellphone, birthdate, gender) VALUES ('42506478116','Fabiana','Liz','21995771721','1994-02-05','F'), ('59665195522','Gabriel','Sebastião','82991623020','1973-09-25','M'), ('42742799958','Nair','Luciana','61997673759','1991-09-01','F'), ('72864703530','Miguel','Luís','42984444696','1960-06-04','M'), ('14437519795','Liz','Nicole','21995730097','1983-03-17','F'), ('94983784527','Luzia','Analu','55986306237','1996-09-16','F'), ('16455881141','Marcos','Vitor','68987657137','1975-03-12','M'), ('12036284477','Renata','Analu','86995373543','1974-05-16','F'), ('56557865595','Marli','Ayla','96987338712','1958-03-24','F'), ('52990623540','Luan','César','69984016265','1971-08-23','M'), ('90817179607','Benedito','Marcos','79999237599','1955-01-23','M'), ('61556026463','Gabriel','Gabriel','62996805016','1988-04-22','M'), ('67820589957','César','Iago','65989341285','1988-04-13','M'), ('20790589168','Tomás','Danilo','86986865660','1971-01-26','M'), ('98782486077','Carla','Sara','98992530055','1957-07-09','F'), ('84924635030','Nathan','Márcio','96992723116','1948-10-09','M'), ('21125543604','Tomás','Fábio','31994020019','1954-01-06','M'), ('70260605204','Antonella','Renata','44997374183','1977-09-17','F'), ('40677405812','Luiz','Renato','92989635577','1976-07-25','M'), ('13082255809','Yago','João','43991018836','2002-04-08','M'), ('77415125525','Rita','Laura','61987169501','1968-08-27','F'), ('81395206910','Luciana','Aurora','32986209581','1948-02-11','F'), ('52839710900','Filipe','Thales','82984644299','1953-04-01','M'), ('68918737700','Rita','Lavínia','35983503467','1963-01-27','F'), ('33833108924','Filipe','Marcos','82985513245','1962-06-23','M'), ('69744913878','Carolina','Silvana','69994618983','1945-07-19','F'), ('39466692550','André','Geraldo','85982516911','1981-11-22','M'), ('96434531702','Mirella','Luiza','34981772327','1954-08-24','F'), ('96486577266','Antonella','Manuela','81986678683','1992-06-11','F'), ('73943198570','Simone','Milena','48992563753','1992-02-01','F'), ('63983160298','Daiane','Renata','24997709226','1968-09-27','F'), ('62211246893','Gael','Breno','62981881541','1977-10-01','M'), ('87271973443','Vicente','Lucca','68999396018','1987-09-12','M'), ('46683990867','Kevin','Gabriel','92997687356','1999-03-26','M'), ('59580445400','Leonardo','Osvaldo','92995487815','1997-10-20','M'), ('90509378455','Luiz','Paulo','69994134960','1974-05-18','M'), ('20085185159','Daniel','Eduardo','86981940569','1991-07-11','M'), ('59021261006','Vera','Bárbara','31988989939','1944-02-16','F'), ('14330771918','Thales','Vicente','71988611281','1943-02-21','M'), ('36092222043','Calebe','Henry','92993621037','1979-04-20','M'), ('43774044660','Lúcia','Marcela','95999084540','1977-07-22','F'), ('75615694912','Sophia','Ester','43985474291','2001-10-21','F'), ('86220612213','Regina','Luzia','61985049956','1998-03-27','F'), ('60134314182','Marcelo','Iago','63988487952','1985-11-10','M'), ('18559588728','Antonio','Alexandre','53985766264','1965-06-08','M'), ('35089452270','Lara','Sônia','83994332518','1987-03-08','F'), ('15308626634','Patrícia','Melissa','86991555594','1988-02-10','F'), ('36351903068','Miguel','Levi','31981776372','1952-11-11','M'), ('67654318960','Allana','Laís','96993430100','1992-05-12','F'), ('61167682530','Agatha','Julia','63995766985','1975-06-17','F'), ('88976488946','Daiane','Milena','67986976554','1967-10-26','F'), ('87398898908','Diogo','Rafael','41987046811','1998-08-12','M'), ('84907703520','Fátima','Cristiane','65996709475','1997-11-11','F'), ('57940334107','Emanuel','Sérgio','81998806731','1962-07-27','M'), ('59500213125','Francisco','Paulo','55983450101','1953-10-03','M'), ('34753587860','Luís','Isaac','68984727671','2004-08-16','M'), ('36997931413','Nair','Bianca','79999557769','1998-06-16','F'), ('85332741227','Elaine','Nicole','98984677339','2005-07-10','F'), ('75848362246','Isabelle','Ayla','47986062122','1977-04-07','F'), ('29981151106','Raimundo','Rafael','82993159917','1993-05-24','M'), ('22521378941','Elias','Fernando','27995215171','1962-07-10','M'), ('88936165801','Daniel','Renato','91983580382','1958-02-09','M'), ('27544417433','Levi','Renan','65994873497','1978-09-17','M'), ('96085116152','Kaique','Antonio','33984735863','1961-03-19','M'), ('79777875126','Antonella','Ana','61982883394','1983-07-17','F'), ('99845373402','Yuri','Oliver','48981299948','1989-07-08','M'), ('55492278606','Josefa','Julia','96983448940','1990-07-24','F'), ('59016675221','Francisco','Heitor','81988761951','1979-03-10','M'), ('86420012163','Marcelo','Osvaldo','63991431934','1943-07-20','M'), ('34020580499','Levi','Edson','95985646688','1990-10-04','M'), ('13716588032','Benedito','Geraldo','81991324736','1947-01-17','M'), ('12035727430','Manuel','Vinicius','81996379151','1981-01-14','M'), ('25324042480','Guilherme','Bento','69996647036','1962-06-07','M'), ('94050782081','Rayssa','Sandra','64995041903','1965-05-16','F'), ('36375958423','Rayssa','Giovana','61993200837','1987-05-26','F'), ('48320983495','Giovanna','Jennifer','95987045032','1963-01-11','F'), ('78748048801','Benjamin','Gustavo','84984721222','1997-01-07','M'), ('22149965593','Eduardo','Severino','84992458551','1952-10-15','M'), ('34877913408','Caroline','Milena','81984532559','1991-10-03','F'), ('97062351598','Brenda','Eloá','62997857642','1985-11-09','F'), ('97002283804','Emily','Analu','66985549535','1973-11-07','F'), ('61539946878','Isabella','Alícia','88996642456','1958-09-13','F'), ('79683808476','Carolina','Evelyn','95998799647','1968-07-08','F'), ('62246146100','Cláudia','Ester','83987668609','1965-08-01','F'), ('91365592618','Cláudio','Juan','84983100843','1991-10-13','M'), ('18769219940','Francisco','Miguel','92984465561','1962-07-22','M'), ('53053762463','Bianca','Tatiane','31984126261','1943-10-24','F'), ('80209976608','Emilly','Lavínia','61997950943','1986-04-05','F'), ('56281469437','Felipe','Diogo','27995092461','1960-02-24','M'), ('68713093061','Francisca','Lorena','69995617131','2004-06-09','F'), ('34348503842','Bruno','Manuel','83999891875','1948-05-16','M'), ('78325932023','Gabriel','Igor','48995690470','2001-10-22','M'), ('97348799957','Evelyn','Betina','27994836694','2002-10-18','F'), ('83460211431','Pietro','Geraldo','41984867029','2002-11-16','M'), ('22713564239','Raimundo','Pietro','66982868901','1970-03-08','M'), ('97529241311','Letícia','Stefany','61988009525','1967-10-18','F'), ('88528263274','Carla','Elza','63996492567','1958-02-06','F'), ('33822710369','Danilo','Nathan','85995441961','2001-09-07','M'), ('82758275902','Josefa','Bianca','67997995081','1966-08-17','F'), ('62337555062','Tiago','Enrico','65982383808','1995-10-09','M'), ('54757333501','Carlos','Filipe','92985252769','1980-06-24','M'), ('23692053002','Yago','Kaique','27999665984','1979-04-09','M'), ('20540740209','Joana','Olivia','67996776063','1984-05-24','F'), ('43180965991','Benedita','Tânia','63996175250','1982-01-07','F'), ('28064794102','Noah','Rafael','68997404081','1984-01-05','M'), ('65093295655','Francisco','Nelson','91997371898','1959-10-20','M'), ('39376262042','Márcia','Luna','98992588818','1951-03-04','F'), ('61682387860','Manoel','Augusto','67994841813','1957-10-25','M'), ('23355985708','Anderson','Renan','69995394246','1990-07-21','M'), ('70473027607','Tereza','Melissa','79998540951','1957-02-14','F'), ('58799407809','Andreia','Yasmin','62996724919','1985-01-20','F'), ('25668243630','Giovanni','Lorenzo','21987713802','1982-10-15','M'), ('11142312402','Bruna','Maitê','34987881728','1945-10-25','F'), ('52983224848','Giovana','Tânia','92982951193','1951-11-09','F'), ('87990072076','Nathan','Igor','77994616032','1980-03-03','M'), ('46452891448','Leonardo','Mateus','31994582652','1975-09-10','M'), ('38538485407','Pedro','Henrique','91985078485','2000-06-01','M'), ('80392207850','Henry','Bernardo','99987035154','1949-08-09','M'), ('63974837892','Eduardo','Mário','63984434985','2002-11-20','M'), ('45488007784','Renan','Victor','27991528380','1950-02-24','M'), ('25064510055','Manuela','Luciana','71993299284','1982-03-15','F'), ('14029972560','Lucca','Gael','54992100446','1996-01-17','M'), ('66933450341','Ricardo','Marcos','83996499379','1965-08-09','M'), ('67666930453','Samuel','Benício','96989338361','1950-03-01','M'), ('95094074962','Emanuel','Enzo','22993853489','1984-09-27','M'), ('40656708107','Mariane','Adriana','61989205229','1948-03-27','F'), ('37979779568','Gael','Pedro','69997991249','1974-03-21','M'), ('33662942569','Juan','Bernardo','85985306617','1967-06-12','M'), ('28182701708','Manoel','Bryan','94983880559','1997-06-22','M'), ('68576474530','Louise','Sabrina','77982543673','1971-04-27','F'), ('38586549819','Carolina','Sophia','34981674864','1982-06-19','F'), ('94103669179','Agatha','Elaine','84989820550','1960-11-18','F');
            """;



}
