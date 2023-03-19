public class MainDeadlock {

    public static void main(String[] args) {

        MainDeadlock mainDeadlock = new MainDeadlock();
        Account account1 = mainDeadlock.new Account();
        Account account2 = mainDeadlock.new Account();

        double sum = 1000;

        new Thread(() -> {
            try {
                mainDeadlock.transfer(sum, account1, account2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                mainDeadlock.transfer(sum, account2, account1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    void transfer(double sum, Account accountFrom, Account accountTo) throws InterruptedException {
        synchronized (accountFrom) {
            accountFrom.withdraw(sum);
            Thread.sleep(500);
            synchronized (accountTo) {
                accountTo.deposit(sum);
            }
        }
    }

    class Account {

        double balance;

        void deposit(double sum) {
            balance += sum;
        }

        void withdraw (double sum) {
            balance -= sum;
        }
    }
}
