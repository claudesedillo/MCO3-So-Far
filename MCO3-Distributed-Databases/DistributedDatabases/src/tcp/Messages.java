package tcp;

public class Messages {
	public final static String READY = "ready";
	
	public final static String COMMIT_FOR_MAIN = "commit for main";
	public final static String COMMIT_FOR_REPLICA = "commit for replica";
	public final static String ABORT = "abort";
	
	public final static String QUERY_FOR_MAIN = "query for main";
	public final static String QUERY_FOR_REPLICA = "query for replica";
	public final static String QUERY_FOR_SELECT = "query for select";
	public final static String QUERY_FOR_SELECT_REPLICA = "query for selreplica";
	
	public final static String JUST_CONTINUE = "continue";
	
	public static boolean isCommitOrAbort(String message) {
		return message.contains(COMMIT_FOR_MAIN) || message.contains(COMMIT_FOR_REPLICA) || message.contains(ABORT);
	}
}
