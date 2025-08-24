package service;

import gestionclientes.model.Usuario;
import gestionclientes.model.Rol;

public class SistemaGestionClientes {
    private Usuario[] usuarios;
    private int contador;

    public SistemaGestionClientes() {
        this.usuarios = new Usuario[50]; // máx 50 usuarios
        this.contador = 0;
    }

    public boolean agregarUsuario(Usuario u) {
        if (contador >= usuarios.length) {
            System.out.println("No se pueden agregar más usuarios (límite alcanzado).");
            return false;
        }
        if (buscarPorUsername(u.getUsername()) != null) {
            System.out.println("Error: El username ya está en uso.");
            return false;
        }
        usuarios[contador++] = u;
        return true;
    }

    public Usuario buscarPorUsername(String username) {
        for (int i = 0; i < contador; i++) {
            if (usuarios[i].getUsername().equals(username)) {
                return usuarios[i];
            }
        }
        return null;
    }

    public Usuario buscarPorId(int id) {
        for (int i = 0; i < contador; i++) {
            if (usuarios[i].getId() == id) {
                return usuarios[i];
            }
        }
        return null;
    }

    public boolean eliminarUsuario(String username) {
        for (int i = 0; i < contador; i++) {
            if (usuarios[i].getUsername().equals(username)) {
                // Verificar si es admin
                if (usuarios[i].getRol() == Rol.ADMIN) {
                    System.out.println("Error: No se puede eliminar al usuario ADMIN.");
                    return false;
                }
                usuarios[i].registrarAccion("Usuario eliminado del sistema");
                for (int j = i; j < contador - 1; j++) {
                    usuarios[j] = usuarios[j + 1];
                }
                usuarios[contador - 1] = null;
                contador--;
                return true;
            }
        }
        return false;
    }

    public Usuario login(String username, String password) {
        for (int i = 0; i < contador; i++) {
            Usuario u = usuarios[i];
            if (u.getUsername().equals(username) && u.validarPassword(password)) {
                u.registrarAccion("Inicio de sesión exitoso");
                return u;
            }
        }
        return null;
    }

    public void listarUsuarios() {
        System.out.println("--- Lista de usuarios ---");
        for (int i = 0; i < contador; i++) {
            System.out.println(usuarios[i].getId() + " | " + usuarios[i].getUsername() + " | " + usuarios[i].getRol());
        }
    }
}

