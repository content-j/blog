
package test;
 
public class Account {
private int account;
 
public Account(int i){
    account = i;
    System.err.println("생성자호출:"+account);
}
public int getBalance(){
    return account;
}
 
/** 출금 **/
public void withdraw(int i) {
    account =account- i;
 
}
/** 예금 **/
public void deposit(int i) {
    account=account+i;
 
}
}