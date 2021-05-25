package ru.poetofcode.rx_samples;

import io.reactivex.Observable;

public class Launcher {

    public static void main(String[] args) {

        // Observable<String> names = Observable.just("One", "Two", "Three");

        Observable<String> names =

        Observable.create(emitter -> {
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
                // return Observable.error(new Exception("Error in map"));
                throw new Exception("kjkljlk");
            }
        });

        lens.blockingSubscribe(name -> {
            log("Name: " + name);
        }, e -> {
            log("Exception: " + e.toString());
        }, () -> {
            log("Completed !");
        });


        /* Observable<Integer> lens = names.map(String::length); */

//        names.blockingSubscribe(name -> {
//            log("Name: " + name);
//        }, e -> {
//            log("Exception: " + e.toString());
//        }, () -> {
//            log("Completed!");
//        });

//        lens.blockingSubscribe(len -> {
//            log("len: " + len);
//        });

        log("FINISH APP");
    }

    private static void log(String str) {
        System.out.println(str);
    }

}