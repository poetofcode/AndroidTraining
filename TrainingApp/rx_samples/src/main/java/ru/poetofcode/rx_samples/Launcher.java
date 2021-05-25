package ru.poetofcode.rx_samples;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

public class Launcher {

    private static int currentSample = 1;

    private static ObservableEmitter<String> convertedEmitter;

    public static void main(String[] args) {

        sampleOne_Errors();

        sampleTwo_Creation();

        sampleThree_ProxyObservable();

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

    private static void sampleThree_ProxyObservable() {
        logSampleHeader();

        Observable<String> strings = Observable.just("Red", "Blue", "White");

        convertObservable(strings).subscribe(Launcher::log).dispose();
    }

    private static Observable<String> convertObservable(Observable<String> source) {
        Observable<String> res = Observable.create(emitter -> {
            Launcher.convertedEmitter = emitter;

            Disposable d = source.subscribe(str -> {
                Launcher.convertedEmitter.onNext("converted-" + str);
            }, Throwable::printStackTrace, () -> Launcher.convertedEmitter.onComplete());

            emitter.setDisposable(d);
        });

        return res;
    }

}