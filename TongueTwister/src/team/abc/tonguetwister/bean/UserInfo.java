package team.abc.tonguetwister.bean;

import java.io.Serializable;

public class UserInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private long userId;
  private String userName;
  private int userGender;
  private int challengePassNum;
  
  public long getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(long userId)
  {
    this.userId = userId;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public int getUserGender()
  {
    return this.userGender;
  }
  
  public void setUserGender(int userGender)
  {
    this.userGender = userGender;
  }
  
  public int getChallengePassNum()
  {
    return this.challengePassNum;
  }
  
  public void setChallengePassNum(int challengePassNum)
  {
    this.challengePassNum = challengePassNum;
  }
  
  public String toString()
  {
    return 
    
      "UserInfo [userId=" + this.userId + ", userName=" + this.userName + ", userGender=" + this.userGender + ", challengePassNum=" + this.challengePassNum + "]";
  }
}
