
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import java.time.Instant;
import java.time.Duration;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import java.util.StringJoiner;

import java.util.stream.Stream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Comparator;

public class TestJava {

  public static void main(String[] args) {
    
    testLamda();
    testJavaTime();
    testConcat();
    testIO();
    
  }
  
  public static void testLamda() {
    List<String> maListe = Arrays.asList( "Bonjour", "tout le monde", "ceci est un test", "java 8 rocks !");
    Collections.sort( maListe, (a, b) -> b.length() - a.length() );
    System.out.println( "Chaines triées par taille :" );
    maListe.forEach( System.out::println );
    System.out.println();
  }
  
  public static void testJavaTime() {
    Instant begin = Instant.now();
    System.out.println( "Maintenant (ISO) : " + begin );
    Instant end = Instant.now();
    System.out.println( "Temps d'exécution : " + Duration.between(begin, end).toMillis() + " ms" );
    LocalDate now = LocalDate.now();
    System.out.println( "Lundi prochain : " + now.with(TemporalAdjusters.next(DayOfWeek.MONDAY)) );
    System.out.println();
  }
  
  public static void testConcat() {
    StringJoiner sj = new StringJoiner( ",", "[", "]");
    System.out.println( sj );
    sj.add("One");
    System.out.println( sj );
    sj.add("Two");
    System.out.println( sj );
    sj.add("Three");
    System.out.println( sj );
    System.out.println();
  }
  
  public static void testIO() {
    String home = System.getProperty("user.home");
    System.out.println("List files in '" + home + "':");
    Path path = Paths.get( home );
    try ( Stream<Path> stream = Files.list(path) ) {
      Comparator<Path> directoryFirst = (p1, p2) -> {
        int i = p1.toFile().isDirectory() ? 1 : 0;
        int j = p2.toFile().isDirectory() ? 1 : 0;
        return j - i;
      };
      stream
        .filter( p -> p.getFileName().toString().charAt(0) != '.'  )
        .sorted( 
          directoryFirst.thenComparing( Path::getFileName )
        )
        .map( p -> p.toFile().isDirectory() ? "[D] " + p : " f  " + p )
        .forEach( System.out::println );
    } catch(IOException ioe) {
      System.err.println("Unable to list " + home);
    }
    System.out.println();
  }

}