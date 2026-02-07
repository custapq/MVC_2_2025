# ระบบจัดสรรศูนย์พักพิงในสถานการณ์ฉุกเฉิน 

ข้อสอบ ข้อที่ 2 ของ Exit Exam โดยการเขียนโปรแกรมแบบ MVC สำหรับระบบสัดสรรที่หลบภัยในสถานการณ์ฉุกเฉิน 

## Technology Stack

* **Language:** Java (JDK 17+)
* **Framework:** Spring Boot
* **Database:** SQLite 
* **Build Tool:** Maven
* **Frontend:** Thymeleaf Template Engine

---

### Models (`/models`)
* **`CitizenModel.java`**: เก็บข้อมูลประชาชน (Code, Age, Health, Type) และมี Logic สำคัญคือ `getPriorityScore()` เพื่อคำนวณคะแนนที่ใช้บอกลำดับความสำคัญ
* **`ShelterModel.java`**: เก็บข้อมูลศูนย์พักพิง (Capacity, Occupancy, Risk Level) 
* **`AssignmentModel.java`**: เก็บข้อมูลการจับคู่ว่าประชาชนคนไหน อยู่ที่ศูนย์พักพิงไหน

### Services (`/services`)
* **`AssignmentService.java`**: จัดการ business logic ต่างๆดังนี้
    * `assignCitizenToShelter()`: ตรวจสอบเบื้องต้นว่า ศูนย์เต็มหรือไม่ กลุ่มเสี่ยงไปพื้นที่เสี่ยงสูงหรือไม่
    * `getTopPriorityCitizens()`: กรองเฉพาะประชาชนที่มีคะแนนความสำคัญสูงสุด
    * `getUnassignedCitizensSorted()`: ดึงข้อมูลประชาชนที่ยังไม่ได้ถูกจัดสรรโดยเรียงลำดับตามความสำคัญ

### Controllers (`/controllers`)
* **`CitizenController.java`**: จัดการ  `/citizens`
    * รับข้อมูลลงทะเบียน ตรวจสอบเลขบัตร 13 หลัก และบันทึกลง Database
* **`AssignmentController.java`**: จัดการ `/assign`
    * เตรียมข้อมูลของศูนย์พักพิง และประชนชนที่ยังไม่ได้ถูกจัดสรร จัดการจัดสรรศูนย์พักพิงให้กับประชนชน
* **`ReportController.java`**: จัดการ `/report` 
    * เตรียมข้อมูลของศูนย์พักพิงและประชนชนเพื่อไปแสดงรายงาน

### Views (`/templates`)
* **`citizen.html`**:
    * แบบฟอร์มลงทะเบียนประชาชน
    * ตารางแสดงรายการประชาชนโดยแบ่งแต่ละประเภท
* **`assign.html`**:
    * หน้าจอหลักสำหรับจัดสรรศูนย์พักพิงให้กับประชาชนแต่ละคน
    * จะแสดงเฉพาะรายการที่มีความสำคัญสูงสุดในขณะนั้น
    * ตารางแสดงคิวประชาชนที่รอจัดสรรทั้งหมด เรียงตามลำดับความสำคัญ
    * ตารางแสดงสถานะของศูนย์พักพิง
* **`report.html`**:
    * Dashboard สรุปยอดจำนวนผู้ได้รับที่พักและผู้ตกค้าง
    * ตารางรายการผู้ได้รับที่พักและรายการทั้งหมด

### Repository (`/repositories `)

    มีหน้าที่จัดการระหว่างโปรแกรมกับ Database โดย method เบื้องต้นเช่น 
    save() เพื่อบันทึกข้อมูล findAll() เพื่อดึงข้อมูลทั้งหมด,findById(id) เพื่อดึงข้อมูลราย id,existsById(id) เพื่อใช้ตรวจสอบว่ามี id แล้วรึยัง

* **`CitizenRepository.java`**:
    * จัดการข้อมูลใน table Citizens
    * findUnassignedCitizens() ใช้ในการหา citizen ที่ยังไม่ได้ถูก assign
* **`ShelterRepository.java`**:
    * จัดการข้อมูลใน table Shelters
* **`AssignmentRepository.java`**:
    * จัดการข้อมูลใน table Assignment
    * existsByCitizenCode(code) ตรวจสอบว่า citizen code นี้ถูก assign แล้วรึยัง
---

## How to Run

1.  Clone Repository
2.  Open in IDE (VS Code / IntelliJ / Eclipse)
3.  Run `./mvnw spring-boot:run`
4.  เปิด Browser และไปยัง: `http://localhost:8080/` (หน้าแรก)