package team.abc.tonguetwister.bean;


public class TongueTwisterDetails {
	private Integer number;
	private float ratingNum;
	private Integer isPassThrough;
	private Integer isCollect;

	public Integer getIsPassThrough() {
		return isPassThrough;
	}

	public void setIsPassThrough(Integer isOperation) {
		this.isPassThrough = isOperation;
	}

	public float getRatingNum() {
		return ratingNum;
	}

	public void setRatingNum(float ratingNum) {
		this.ratingNum = ratingNum;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}

}
