
package com.sistemaventa.objects;

/**
 *
 * @author elvis_agui
 */
public class Usuario {
    private String nombre;
    private String password;
    private int rol;

    public Usuario(){
        
    }

    public Usuario(String nombre, String password, int rol) {
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }
    
    public String rolString(){
        if (this.rol == 1) {
            return "Encargado";
        } else {
            return  "Empleado";
        }
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", password=" + password + ", rol=" + rol + '}';
    }
    
    
    
}
