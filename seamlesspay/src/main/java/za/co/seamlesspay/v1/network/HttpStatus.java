package za.co.seamlesspay.v1.network;

/**
 * A HTTP status code enum class, that holds all the HTTP Status codes the app deals with.
 */
public interface HttpStatus {

  /**
   * HTTP Status-Code 200: OK.
   */
  int OK = 200;

  /**
   * HTTP Status-Code 201: Created.
   */
  int CREATED = 201;

  /**
   * HTTP Status-Code 202: Accepted.
   */
  int ACCEPTED = 202;

  /**
   * HTTP Status-Code 204: No Content.
   */
  int NO_CONTENT = 204;

  /**
   * HTTP Status-Code 300: Multiple Choices.
   */
  int MULTI_CHOICE = 300;

  /**
   * HTTP Status-Code 400: Bad Request.
   */
  int BAD_REQUEST = 400;

  /**
   * HTTP Status-Code 401: Unauthorized.
   */
  int UNAUTHORIZED = 401;

  /**
   * HTTP Status-Code 403: Forbidden.
   */
  int FORBIDDEN = 403;

  /**
   * HTTP Status-Code 404: Not Found.
   */
  int NOT_FOUND = 404;

  /**
   * HTTP Status-Code 500: Internal Server Error.
   */
  int INTERNAL_SERVER_ERROR = 500;

  /**
   * HTTP Status-Code 503: Service Unavailable.
   */
  int UNAVAILABLE = 503;
}
