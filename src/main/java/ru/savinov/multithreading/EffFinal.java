package ru.savinov.multithreading;

/**
 * Race condition = Общие данные + Изменяемость + Многопоточность
 */
public class EffFinal {
    private static final String STATIC_VAR = "var";

    public static void main(String[] args) {
 // Пытаемся создать замыкание
    }

    // эффективно финальная - запрещено менять
    public void effFinal() {
        int x = 10;
        Runnable r = () -> System.out.println(x);
//        x++; запрещено изменять копию локальной переменной захваченной лямбда функцией
    }

    //замыкание когда есть переменные из внешнего контекста
    // в текущем примере замыкания нет
    public void outVariable() {
        int x = 10;
        Runnable r = () -> System.out.println(x);
    }

    //не замыкание - переменная статическая финальная
    public void staticVariable() {
        Runnable r = () -> System.out.println(STATIC_VAR);
    }
}
