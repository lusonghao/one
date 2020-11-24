package com.tanxin.manage_course.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Scanner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test2 {

    @Test
    public static void main(String[] args) {
        int n;
        int[] arr = new int[5];
        Scanner sc = new Scanner(System.in);
        int a = 0;
        for (int i : arr) {
            a=a+1;
            System.out.println(i+a);
        }



    }
}
