package com.library_practice.step_definitions;

import com.library_practice.utilities.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class US_4 {
    String studentName;
    @When("I execute a query to find the most popular user")
    public void i_execute_a_query_to_find_the_most_popular_user() {
        DB_Util.runQuery("select full_name,count(*) from users u inner join book_borrow bb on u.id = bb.user_id \n" +
                "group by full_name \n" +
                "order by 2 desc");
         studentName = DB_Util.getCellValue(1,"full_name");
     /*   int num=0;
        for(int i =0; i<studentName.length(); i++ ){
            if(Character.isDigit(studentName.charAt(i))){

                num =Integer.parseInt(""+studentName.charAt(i));

            }
        }
        System.out.println("Student number is : "+num);
        System.out.println("Most popular user : "+studentName);
      */


    }
    @Then("verify {string} is the user who reads the most")
    public void verify_is_the_user_who_reads_the_most(String string) {
        Assert.assertEquals(studentName,string);
    }
}
