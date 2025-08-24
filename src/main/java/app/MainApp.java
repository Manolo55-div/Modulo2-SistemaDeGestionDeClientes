package app;

import gestionclientes.model.Usuario;
import gestionclientes.model.Rol;
import service.SistemaGestionClientes;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaGestionClientes sistema = new SistemaGestionClientes();

        // Crear admin por defecto
        sistema.agregarUsuario(new Usuario(1, "Administrador Principal", "admin", "1234", Rol.ADMIN));

        int idCounter = 2;

        while (true) {
            System.out.println("\n--- Sistema de Gestión de Clientes ---");
            System.out.print("Usuario: ");
            String user = sc.nextLine();
            System.out.print("Contraseña: ");
            String pass = sc.nextLine();

            Usuario logueado = sistema.login(user, pass);

            if (logueado != null) {
                System.out.println("\nBienvenido " + logueado.getNombreCompleto() + " (" + logueado.getRol() + ")");
                boolean salir = false;

                while (!salir) {
                    if (logueado.getRol() == Rol.ADMIN) {
                        System.out.println("\n1. Crear usuario");
                        System.out.println("2. Listar usuarios");
                        System.out.println("3. Eliminar usuario");
                        System.out.println("4. Ver historial de un usuario");
                        System.out.println("5. Cerrar sesión");
                        System.out.print("Opción: ");
                        int op = Integer.parseInt(sc.nextLine());

                        switch (op) {
                            case 1:
                                System.out.print("Nombre completo: ");
                                String nombre = sc.nextLine();
                                System.out.print("Username: ");
                                String username = sc.nextLine();
                                System.out.print("Password: ");
                                String password = sc.nextLine();
                                System.out.print("Rol (1=ADMIN, 2=ESTANDAR): ");
                                int r = Integer.parseInt(sc.nextLine());
                                Rol rol = (r == 1) ? Rol.ADMIN : Rol.ESTANDAR;

                                Usuario nuevo = new Usuario(idCounter++, nombre, username, password, rol);
                                if (sistema.agregarUsuario(nuevo)) {
                                    logueado.registrarAccion("Creó usuario " + username);
                                }
                                break;

                            case 2:
                                sistema.listarUsuarios();
                                break;

                            case 3:
                                System.out.print("Username a eliminar: ");
                                String userDel = sc.nextLine();
                                if (sistema.eliminarUsuario(userDel)) {
                                    logueado.registrarAccion("Eliminó usuario " + userDel);
                                } else {
                                    System.out.println("No se encontró el usuario.");
                                }
                                break;

                            case 4:
                                System.out.print("Username a consultar historial: ");
                                String userHist = sc.nextLine();
                                Usuario u = sistema.buscarPorUsername(userHist);
                                if (u != null) {
                                    u.mostrarHistorial();
                                } else {
                                    System.out.println("No se encontró el usuario.");
                                }
                                break;

                            case 5:
                                salir = true;
                                break;
                        }
                    } else {
                        System.out.println("\n1. Ver historial propio");
                        System.out.println("2. Cambiar nombre completo");
                        System.out.println("3. Cambiar contraseña");
                        System.out.println("4. Cerrar sesión");
                        System.out.print("Opción: ");
                        int op = Integer.parseInt(sc.nextLine());

                        switch (op) {
                            case 1:
                                logueado.mostrarHistorial();
                                break;

                            case 2:
                                System.out.print("Nuevo nombre completo: ");
                                String nuevoNombre = sc.nextLine();
                                logueado.setNombreCompleto(nuevoNombre);
                                break;

                            case 3:
                                System.out.print("Contraseña actual: ");
                                String actual = sc.nextLine();
                                System.out.print("Nueva contraseña: ");
                                String nueva = sc.nextLine();
                                if (!logueado.cambiarPassword(actual, nueva)) {
                                    System.out.println("Contraseña actual incorrecta.");
                                }
                                break;

                            case 4:
                                salir = true;
                                break;
                        }
                    }
                }
            } else {
                System.out.println("Credenciales inválidas. Intente de nuevo.");
            }
        }
    }
}
