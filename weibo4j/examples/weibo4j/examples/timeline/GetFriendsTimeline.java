/**
 * 
 */
package weibo4j.examples.timeline;

import java.util.List;

import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;

/**
 * @author sina
 *
 */
public class GetFriendsTimeline {

	/**
	 * 获取当前用户所关注用户的最新微博信息 (别名: statuses/home_timeline) 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		
		try {
			//获取前20条关注用户的微博信息
			Weibo weibo = new Weibo();
			weibo.setToken("7c3909e450590471db6cf2d4c8dd9f64","59a4f8f742e1b6f1bb1f4d0c4076a5ed");
			Paging page=new Paging(2);
			List<Status> statuses = weibo.getFriendsTimeline(page);
			for (Status status : statuses) {
	            System.out.println(status.getUser().getName() + ":" +
	                               status.getText());
	        }
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
