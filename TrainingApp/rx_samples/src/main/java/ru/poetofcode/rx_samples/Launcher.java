package ru.poetofcode.rx_samples;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

public class Launcher {

    private static int currentSample = 1;

    public static void main(String[] args) {

        sampleOne_Errors();

        sampleTwo_Creation();

        log("\nFINISH APP");
    }

    private static void log(String str) {
        System.out.println(str);
    }

    private static void logSampleHeader() {
        log("\n*********** #" + currentSample++ + " *************");
    }

    private static void sampleOne_Errors() {
        logSampleHeader();

        Observable<String> names = Observable.create(emitter -> {
            emitter.onNext("One");
            emitter.onNext("Two");
            emitter.onNext("Three");

            emitter.onError(new Exception("Some error occurred!"));

//            if (1 == 2 - 1) {
//                throw new Exception("Ex");
//            }

            emitter.onComplete();
        });

        Observable<Integer> lens = names.map(item -> {
            if (item instanceof String) {
                return ((String) item).length();
            } else {
                throw new Exception("Error description");
            }
        });

        lens.blockingSubscribe(name -> {
            log("Name: " + name);
        }, e -> {
            log("Exception: " + e.toString());
        }, () -> {
            log("Completed !");
        });
    }

    @SuppressWarnings("CheckResult")
    private static void sampleTwo_Creation() {
        logSampleHeader();

        /*
            Factory methods:

            - create
            - just
            - fromIterable
            - fromCallable (что это?)
            - defer (что это?)
            - range (??)
            - interval
            - ConnectableObservable
         */

        List<String> lines = Arrays.asList("One", "Two", "Three");

        Observable.fromIterable(lines)
                .subscribe(Launcher::log);

        Observable.just("1", "2", "3")
            .subscribe(Launcher::log);


        Observable<String> helloWorld = Observable.fromIterable(
                Arrays.asList("H", "e", "l", "l", "o", " ", "W", "o", "r", "l", "d")
        );

        ConnectableObservable<String> helloHot = helloWorld.publish();

        helloHot.subscribe(System.out::print, e -> {}, () -> log(""));

        helloHot.connect();
    }

}