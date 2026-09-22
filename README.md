# VD2 - Custom Login Spring Boot 4 + Spring Security

**Sinh viên:** Trần Quyết Chiến  
**MSSV:** 24110171  
**Lớp:** LTWeb 09

## Nội dung đã hoàn thiện

Project thực hiện đầy đủ chức năng chính của VD2 trong tài liệu:

- Custom login bằng **username hoặc email**.
- Spring Boot 4.1.1 + Spring Security.
- `User`, `Role`, `UserDTO`, `LoginDTO`.
- MapStruct `User -> UserDTO`.
- `CustomUserDetails` để đưa `fullName`, `images`, `username`, `email`, `role` ra Thymeleaf.
- Thymeleaf + Layout Dialect.
- Header hiển thị avatar, họ tên, username, email, role.
- Logout.
- SQL Server có sẵn bảng và dữ liệu mẫu.

## Điểm khác với PDF để project chạy ổn trên STS

Theo yêu cầu trước đó, project **không dùng JPA/Hibernate**. Repository trong bài được triển khai bằng `JdbcTemplate`, nhưng luồng chức năng VD2 vẫn giữ nguyên. Project cũng **không dùng Lombok** để tránh lỗi annotation processing trong STS. MapStruct vẫn được giữ theo yêu cầu của VD2.

## 1. Tạo database

Trong SQL Server Management Studio, chạy:

`database/01_create_database_and_sample_data.sql`

Script này **KHÔNG thay đổi password hoặc trạng thái của login `sa`**. Nó chỉ tạo database `webst9` và 2 bảng `roles`, `users`.

## 2. Cấu hình SQL Server

`src/main/resources/application.properties` đang để:

- Database: `webst9`
- SQL user: `sa`
- SQL password: `chien722006`
- Web port: `8081`

Nếu SQL Server của bạn đang dùng password `sa` khác thì chỉ sửa đúng dòng `spring.datasource.password`.

## 3. Import vào STS

1. `File -> Import...`
2. `Maven -> Existing Maven Projects`
3. Chọn folder chứa `pom.xml`
4. `Finish`
5. Chuột phải project -> `Maven -> Update Project...`
6. Tick `Force Update of Snapshots/Releases` -> `OK`
7. `Project -> Clean...`
8. Chờ Maven tải xong dependency.
9. Chạy `Springboot19Application.java` -> `Run As -> Spring Boot App`

Nếu `UserMapperImpl` chưa sinh ngay trong STS: chuột phải project -> `Maven -> Update Project`, sau đó `Project -> Clean`. Project đã kèm cấu hình APT cho STS/Eclipse.

## 4. Chạy web

Mở:

`http://localhost:8081/login`

Tài khoản sinh viên:

- Username: `24110171`
- Email: `24110171@student.local`
- Password: `123456`

Có thể đăng nhập bằng **username hoặc email**.

Tài khoản admin:

- Username: `admin`
- Email: `admin@vd2.local`
- Password: `123456`

## Luồng hoạt động

`login.html -> Spring Security -> CustomUserDetailsService -> UserRepository (JdbcTemplate) -> SQL Server -> CustomUserDetails -> home/header`
