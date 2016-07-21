package team.abc.tonguetwister.bean;


public class TongueTwister {
	private Integer id;
	private String title;
	private String content;
	private boolean collectionStatus = false; 
		
	public TongueTwister(String title , String content){
		this.title = title;
		this.content = content;
	}
	
	public TongueTwister(String title , String content , Integer id){
		this.title = title;
		this.content = content;
		this.id = id;
	}

	public TongueTwister(Integer id){
		this.id = id;
	}
	
	public TongueTwister(){
		
	}
	
	public void setCollectionStatus(Boolean b){
		this.collectionStatus = b;
	}
	
	public boolean getCollectionStatus(){
		return collectionStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "TongueTwister [id=" + id + ", title=" + title + ", content="
				+ content + "]";
	}
	/**
	 * @author zsc
	 * 		通过ID比较绕口令是否是同一个。
	 */
	@Override
	public boolean equals(Object o) {

		if(o instanceof TongueTwister){
			
			if(this.getId().equals(((TongueTwister)o).getId())){
				return true;				
			}
		}
		return false;
		
	}
	
	
}
