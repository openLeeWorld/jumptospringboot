package com.mysite.sbb;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Setter
public class HelloLombok {
    private final String hello;
    private final int lombok;

    public static void main(String[] args) {
        //HelloLombok helloLombok = new HelloLombok();
        //helloLombok.setHello("헬로");
        //helloLombok.setLombok(5);
        
        HelloLombok helloLombok2 = new HelloLombok("헬로2", 10);

        //System.out.println(helloLombok.getHello());
        //System.out.println(helloLombok.getLombok());
        System.out.println(helloLombok2.getHello());
        System.out.println(helloLombok2.getLombok());
    }
}
