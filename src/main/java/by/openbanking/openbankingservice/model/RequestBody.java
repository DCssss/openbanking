package by.openbanking.openbankingservice.model;

public interface RequestBody<T1 extends RequestBody.Data, T2 extends RequestBody.Risk> {

    interface Data {
    }

    interface Risk {
    }

    T1 getData();

    T2 getRisk();
}
