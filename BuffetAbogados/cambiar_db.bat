@echo off
echo ====================================
echo  BUFFET DE ABOGADOS - CAMBIO DE BD
echo ====================================
echo.
echo 1. SQLite (Desarrollo/Pruebas)
echo 2. MySQL (Produccion)
echo.
set /p opcion="Selecciona una opcion (1 o 2): "

if "%opcion%"=="1" (
    echo tipoDB=SQLite > configuracion.properties
    echo host=localhost >> configuracion.properties
    echo puerto=3306 >> configuracion.properties
    echo nombreDB=buffet_abogados >> configuracion.properties
    echo usuario=root >> configuracion.properties
    echo password= >> configuracion.properties
    echo timeout=30 >> configuracion.properties
    echo autocommit=true >> configuracion.properties
    echo.
    echo ✅ Configurado para SQLite
) else if "%opcion%"=="2" (
    copy cambiar_a_mysql.properties configuracion.properties
    echo.
    echo ✅ Configurado para MySQL
    echo ⚠️  RECUERDA: Editar la contraseña en configuracion.properties
) else (
    echo ❌ Opcion invalida
)

echo.
pause