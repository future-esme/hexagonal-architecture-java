package md.rald.esme.adapter.in.rest.common;

public record ErrorEntity(int httpStatus, String errorMessage) {
}