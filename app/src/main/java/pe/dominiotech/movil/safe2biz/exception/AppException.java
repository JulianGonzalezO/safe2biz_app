package pe.dominiotech.movil.safe2biz.exception;



public class AppException extends RuntimeException {
	protected String code;

	public AppException(String code) {
		this.code = code;
	}  

	public String getMessage() {
		return code;
	}
	public String getCode(){
		return code;
	}
	
}
