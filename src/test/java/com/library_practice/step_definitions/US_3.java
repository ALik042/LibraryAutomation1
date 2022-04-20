package com.library_practice.step_definitions;

import com.library_practice.utilities.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class US_3 {
    String mostPopularGenre;
    @When("I execute a query to find the most popular book genre")
    public void iExecuteAQueryToFindTheMostPopularBookGenre() {
        DB_Util.runQuery("select bc.name,count(*) from book_borrow bb\n" +
                "    inner  join books b on bb.book_id = b.id\n" +
                "    inner join book_categories bc on b.book_category_id=bc.id\n" +
                "group by name\n" +
                "order by 2 desc;");
        mostPopularGenre = DB_Util.getCellValue(1,"name");
        System.out.println(mostPopularGenre);

    }

    @Then("verify that {string} is the most popular book genre.")
    public void verifyThatClassicIsTheMostPopularBookGenre(String str)  {
        System.out.println(str);
        Assert.assertEquals(mostPopularGenre,str);

    }
}
