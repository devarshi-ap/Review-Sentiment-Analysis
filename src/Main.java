class Main{
      public static void main(String[] args){
            
            // show file and it's content
            System.out.printf("File  ==  \"SampleReview.txt\"%n%n");
            System.out.printf("File Content:%n%s", Review.fileToString("SampleReview.txt"));
            
            // total sentiment value of file
            System.out.printf("%n%n%nTotal Sentiment Value of \"SampleReview.txt\"  ==  %.2f", Review.totalSentiment("SampleReview.txt"));
            
            // Negative slanted version of file
            System.out.printf("%n%n%nA more NEGATIVE version of \"SampleReview.txt\" :%n%s", Review.slantMoreNegative("SampleReview.txt"));
                               
            // Positive slanted version of file
            System.out.printf("%n%n%nA more POSITIVE version of \"SampleReview.txt\" :%n%s", Review.slantMorePositive("SampleReview.txt"));
      }
}
