import java.io.*;
import java.util.Scanner;

class Node {
    String song;
    Node next;
    Node prev;

    Node(String song) {
        this.song = song;
        this.next = null;
        this.prev = null;
    }
}

public class PlaylistManager {
    static Node top, temp, top1;

    public static void toFile(String a) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("playlist.txt", true));
            writer.write(a);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addNode(Node first) {
        Scanner scanner = new Scanner(System.in);
        String a;
        while (first.next != null) {
            first = first.next;
        }
        first.next = new Node(null);
        first.next.prev = first;
        first = first.next;
        System.out.print("\nEnter Song name: ");
        a = scanner.nextLine();
        first.song = a;
        toFile(a);
        first.next = null;
    }

    public static void deleteFile(String a) {
        try {
            File inputFile = new File("playlist.txt");
            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String lineToRemove = a;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.equals(lineToRemove)) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);
            if (!successful) {
                System.out.println("Could not rename the file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delNode(Node first) {
        while (first.next.next != null) {
            first = first.next;
        }
        Node temp = first.next;
        first.next = null;
        temp = null;
        System.out.println("Deleted");
    }

    public static void printList(Node first) {
        System.out.println("\nPlaylist Name- ");
        while (first.next != null) {
            System.out.println(first.song);
            first = first.next;
        }
        System.out.println(first.song);
    }

    public static void countNodes(Node first) {
        int i = 0;
        while (first.next != null) {
            first = first.next;
            i++;
        }
        i++;
        System.out.println("\nTotal songs- " + (i - 1));
    }

    public static Node delPos(Node pointer, int pos) {
        Node prev1, temp;
        prev1 = new Node(null);
        temp = new Node(null);
        int i = 0;

        if (pos == 1) {
            temp = pointer;
            deleteFile(temp.song);
            pointer = pointer.next;
            pointer.prev = null;
            return pointer;
        }

        while (i < pos - 1) {
            prev1 = pointer;
            pointer = pointer.next;
            i++;
        }

        if (pointer.next == null) {
            temp = pointer;
            deleteFile(temp.song);
            prev1.next.prev = null;
            prev1.next = null;
        } else {
            temp = pointer;
            deleteFile(temp.song);
            prev1.next = temp.next;
            temp.next.prev = prev1;
        }

        return pointer;
    }

    public static void search(Node first) {
        Scanner scanner = new Scanner(System.in);
        String song;
        System.out.print("\nEnter song to be searched: ");
        song = scanner.nextLine();
        int flag = 0;

        while (first != null) {
            if (first.song.equals(song)) {
                System.out.println("#Song Found");
                flag++;
                break;
            } else {
                first = first.next;
            }
        }
        if (flag == 0) {
            System.out.println("#Song Not found");
        }
    }

    public static void create() {
        top = null;
    }

    public static void push(String data) {
        if (top == null) {
            top = new Node(data);
        } else if (!top.song.equals(data)) {
            temp = new Node(data);
            temp.next = top;
            top = temp;
        }
    }

    public static void display() {
        top1 = top;
        if (top1 == null) {
            System.out.println("=>NO recently played tracks.");
            return;
        }
        System.out.println("=>Recently played tracks-");
        while (top1 != null) {
            System.out.println(top1.song);
            top1 = top1.next;
        }
    }

    public static void play(Node first) {
        Scanner scanner = new Scanner(System.in);
        String song;
        printList(first);
        System.out.print("\nChoose song you wish to play: ");
        song = scanner.nextLine();
        int flag = 0;

        while (first != null) {
            if (first.song.equals(song)) {
                System.out.println("=>Now Playing......" + song);
                flag++;
                push(song);
                break;
            } else {
                first = first.next;
            }
        }
        if (flag == 0) {
            System.out.println("#Song Not found");
        }
    }

    public static void recent() {
        display();
    }

    public static void topElement() {
        top1 = top;
        if (top1 == null) {
            System.out.println("#NO last played tracks.");
            return;
        }
        System.out.println("=>Last Played Song - " + top.song);
    }

    public static void sort(Node pointer) {
        if (pointer == null || pointer.next == null) {
            return; // No need to sort if list is empty or has only one element
        }
    
        boolean sorted = false;
        Node current;
        Node nextTemp = null;
    
        while (!sorted) {
            sorted = true;
            current = pointer;
    
            while (current.next != nextTemp) {
                if (current.song.compareTo(current.next.song) > 0) {
                    // Swap current and current.next
                    String temp = current.song;
                    current.song = current.next.song;
                    current.next.song = temp;
                    sorted = false; // Set sorted to false to continue sorting
                }
                current = current.next;
            }
            nextTemp = current;
        }
    }
    
    
    public static void addNodeFromFile(Node start, String song) {
        while (start.next != null) {
            start = start.next;
        }
        start.next = new Node(song);
        start.next.prev = start;
    }


    public static void addPlaylist(Node start) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("playlist.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                addNodeFromFile(start, line);
            }
            System.out.println("Playlist Added");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delSearch(Node start) {
        Scanner scanner = new Scanner(System.in);
        String song;
        printList(start);
        System.out.print("\nChoose song you wish to delete: ");
        song = scanner.nextLine();
        int flag = 0;
        while (start != null) {
            if (start.song.equals(song)) {
                System.out.println("#Song Found");
                Node temp = start;
                deleteFile(temp.song);
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                flag++;
                break;
            } else {
                start = start.next;
            }
        }
        if (flag == 0) {
            System.out.println("#Song Not found");
        }
    }

    public static void deleteMenu(Node start) {
        Scanner scanner = new Scanner(System.in);
        int c;
        System.out.println("Which type of delete do you want?");
        System.out.println("1. By Search");
        System.out.println("2. By Position");
        c = scanner.nextInt();
        switch (c) {
            case 1:
                delSearch(start);
                break;
            case 2:
                int pos;
                System.out.print("Enter the pos of the song: ");
                pos = scanner.nextInt();
                delPos(start, pos - 1);
                break;
        }
    }

    public static void main(String[] args) {
        int choice;
        Node start, hold;
        start = new Node(null);
        Scanner scanner = new Scanner(System.in);
        System.out.println("\t\t\t**WELCOME**");
        System.out.println("**please use '_' for space.");
        System.out.print("\nEnter your playlist name: ");
        start.song = scanner.nextLine();
        start.next = null;
        hold = start;
        create();

        do {
            System.out.println("\n1. Add New Song");
            System.out.println("2. Delete Song");
            System.out.println("3. Display Entered Playlist");
            System.out.println("4. Total Songs");
            System.out.println("5. Search Song");
            System.out.println("6. Play Song");
            System.out.println("7. Recently Played List");
            System.out.println("8. Last Played");
            System.out.println("9. Sorted playlist");
            System.out.println("10. Add From File");
            System.out.println("11. Exit");
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addNode(start);
                    break;
                case 2:
                    deleteMenu(start);
                    break;
                case 3:
                    printList(start);
                    break;
                case 4:
                    countNodes(hold);
                    break;
                case 5:
                    search(start);
                    break;
                case 6:
                    play(start);
                    break;
                case 7:
                    recent();
                    break;
                case 8:
                    topElement();
                    break;
                case 9:
                    sort(start.next);
                    printList(start);
                    break;
                case 10:
                    addPlaylist(start);
                    break;
                case 11:
                    System.exit(0);
            }
        } while (choice != 11);
    }
}

