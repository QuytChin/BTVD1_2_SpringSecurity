/* ================================================================
   VD2 - CUSTOM LOGIN SPRING SECURITY
   Sinh vien: Tran Quyet Chien
   MSSV: 24110171
   Lop: LTWeb 09

   LUU Y QUAN TRONG:
   - Script nay KHONG doi password tai khoan SQL Server 'sa'.
   - Script chi tao database webst9, bang roles/users va du lieu mau.
   - Chay bang tai khoan SQL Server dang hoat dong cua ban.
   ================================================================ */

USE master;
GO

IF DB_ID(N'webst9') IS NULL
BEGIN
    CREATE DATABASE webst9;
END
GO

USE webst9;
GO

-- Chi reset 2 bang cua bai VD2, KHONG tac dong login SQL Server.
IF OBJECT_ID(N'dbo.users', N'U') IS NOT NULL
    DROP TABLE dbo.users;
GO

IF OBJECT_ID(N'dbo.roles', N'U') IS NOT NULL
    DROP TABLE dbo.roles;
GO

CREATE TABLE dbo.roles (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);
GO

CREATE TABLE dbo.users (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    full_name NVARCHAR(200) NULL,
    images VARCHAR(500) NULL,
    enabled BIT NOT NULL CONSTRAINT DF_users_enabled DEFAULT 1,
    role_id BIGINT NOT NULL,
    CONSTRAINT FK_users_roles FOREIGN KEY (role_id) REFERENCES dbo.roles(id)
);
GO

INSERT INTO dbo.roles(name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');
GO

/* Password web cua cac tai khoan ben duoi la: 123456
   Gia tri luu trong DB la BCrypt de Spring Security kiem tra. */
DECLARE @BCrypt123456 VARCHAR(100) = '$2y$10$bVWW/GUAL2d4dHChaT0hHuiRkO8nikjpdVqF7VwqMT0ogDgrqbvk2';

INSERT INTO dbo.users(username, email, password, full_name, images, enabled, role_id)
SELECT
    '24110171',
    '24110171@student.local',
    @BCrypt123456,
    N'Trần Quyết Chiến',
    '/images/user.svg',
    1,
    id
FROM dbo.roles
WHERE name = 'ROLE_USER';

INSERT INTO dbo.users(username, email, password, full_name, images, enabled, role_id)
SELECT
    'admin',
    'admin@vd2.local',
    @BCrypt123456,
    N'Administrator',
    '/images/avatar-default.svg',
    1,
    id
FROM dbo.roles
WHERE name = 'ROLE_ADMIN';
GO

SELECT * FROM dbo.roles;
SELECT id, username, email, full_name, images, enabled, role_id FROM dbo.users;
GO
