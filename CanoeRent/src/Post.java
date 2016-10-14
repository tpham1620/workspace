public class Post{
		private int cheapest;
		public int lastPost[];
		public Post(int cp, int n){
			setCheapest(cp);
			setLastPost(new int[n]);
		}
		public int getCheapest() {
			return cheapest;
		}
		public void setCheapest(int cheapest) {
			this.cheapest = cheapest;
		}
		public int[] getLastPost() {
			return lastPost;
		}
		public void setLastPost(int lastPost[]) {
			this.lastPost = lastPost;
		}
	}