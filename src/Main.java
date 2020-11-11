class Main{
      public static void main(String[] args){
            
            // show file and it's content
            System.out.println("File: \"SampleReview.txt\"");
            System.out.printf("Content:%n%s", Review.fileToString("SampleReview.txt"));
            
            // total sentiment value of file
            System.out.printf("%n%nTotal Sentiment Value of \"SampleReview.txt\" :  %f%n", Review.totalSentiment("SampleReview.txt"));
            
            // Negative slanted version of file
            System.out.printf("%nA more NEGATIVE version of \"SampleReview.txt\" :%n%s", Review.slantMoreNegative("SampleReview.txt"));
                               
            // Positive slanted version of file
            System.out.printf("%nA more POSITIVE version of \"SampleReview.txt\" :%n%s", Review.slantMorePositive("SampleReview.txt"));
      }
}
