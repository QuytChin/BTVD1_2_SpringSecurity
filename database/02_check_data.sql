USE webst9;
GO

SELECT r.id, r.name FROM dbo.roles r ORDER BY r.id;

SELECT u.id, u.username, u.email, u.full_name, u.images, u.enabled, r.name AS role_name
FROM dbo.users u
JOIN dbo.roles r ON r.id = u.role_id
ORDER BY u.id;
GO
