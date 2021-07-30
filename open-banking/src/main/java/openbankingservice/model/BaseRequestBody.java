package openbankingservice.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseRequestBody<T1 extends RequestBody.Data, T2 extends RequestBody.Risk> implements RequestBody<T1, T2> {

    private final T1 mData;
    private final T2 mRisk;

}
