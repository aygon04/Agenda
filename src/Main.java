import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {

    public static class AddressBook {
        private Map<String, String> contacts = new HashMap<>();
        private String filename = "mi_agenda.csv";
        private Scanner scanner = new Scanner(System.in);


        // Carga los contactos del archivo CSV
        public void load() throws FileNotFoundException {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                contacts.put(parts[0], parts[1]);
            }
        }
        //Metodo extra, edit:
        public void edit() throws IOException {
            System.out.println("Ingrese el número telefónico del contacto a editar:");
            String number = scanner.nextLine();

            // Busca el contacto
            String name = contacts.get(number);
            if (name == null) {
                System.out.println("No se encontró el contacto con el número " + number + ".");
                return;
            }

            // Muestra el nombre actual
            System.out.println("El nombre actual del contacto es " + name + ".");

            // Pide el nuevo nombre
            System.out.println("Ingrese el nuevo nombre del contacto:");
            String newName = scanner.nextLine();

            // Actualiza el nombre
            contacts.put(number, newName);

            // Guarda los cambios
            save();
        }


        // Guarda los cambios en el archivo CSV
        public void save() throws IOException {
            FileWriter writer = new FileWriter(filename);
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
            writer.close();
            System.out.println("Información guardada correctamente.");
        }

        // Muestra los contactos de la agenda
        public void list() {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }

        // Crea un nuevo contacto
        public void create() throws IOException {
            System.out.println("Ingrese el nombre del contacto:");
            String name = scanner.nextLine();
            System.out.println("Ingrese el número telefónico del contacto:");
            String number = scanner.nextLine();
            contacts.put(number, name);
            save();
        }

        // Borra un contacto
        public void delete() throws IOException {
            System.out.println("Ingrese el número telefónico del contacto a borrar:");
            String number = scanner.nextLine();
            contacts.remove(number);
            save();
        }
    }

    public static void main(String[] args) throws IOException {
        AddressBook addressBook = new AddressBook();

        // Crea el archivo CSV automáticamente si no existe
        File file = new File(addressBook.filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("No se pudo crear el archivo CSV.");
            }
        }

        try {
            addressBook.load();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo CSV.");
        }

        // Menú interactivo
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Editar contacto");
            System.out.println("5. Salir");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    addressBook.create();
                    break;
                case 3:
                    addressBook.delete();
                    break;
                case 4:
                    addressBook.edit();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}