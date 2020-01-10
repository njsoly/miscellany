package njsoly.log;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import java.sql.Timestamp;

/**
 *
 * @author njsoly
 * @see org.apache.log4j.Logger Logger
// * @see TestLogger
 */
public class SetupLogger implements Logginit {

	private SetupLogger(){}

	public final static Logger ROOT_LOGGER = Logger.getRootLogger();
	public static final boolean DATES = true;
	String name;
	Layout layout;

	/**
	 * A very basic appender.  Prints everything to standard output, with the level and a timestamp.
	 */
	public static final Appender APPENDER_0 = new Appender() {

		String name = "appender_0";
		Layout l = null;
		Filter headFilter = null;
		ErrorHandler errorHandler = new ErrorHandler() {

			Logger logger = null;
			@Override
			public void setLogger(Logger logger) {
				this.logger = logger;
			}

			@Override
			public void error(String message, Exception e, int errorCode) {

			}

			@Override
			public void error(String message) {

			}

			@Override
			public void error(String message, Exception e, int errorCode, LoggingEvent event) {

			}

			@Override
			public void setAppender(Appender appender) {

			}

			@Override
			public void setBackupAppender(Appender appender) {

			}

			@Override
			public void activateOptions() {

			}
		};

		@Override
		public void setName(String name) {
			this.name = name;

		}

		@Override
		public void setLayout(Layout layout) {
			this.l = layout;
		}

		@Override
		public void setErrorHandler(ErrorHandler arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public ErrorHandler getErrorHandler() {
			return errorHandler;
		}

		@Override
		public boolean requiresLayout() {
			return false;
		}

		/**
		 *
		 */
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return name;
		}

		@Override
		public Layout getLayout() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Filter getFilter() {
			return headFilter;
		}


		@Override
		public void doAppend(LoggingEvent arg0) {
			String claz = arg0.getLoggerName();
			Timestamp time = new Timestamp(arg0.getTimeStamp());
			String lvl = arg0.getLevel().toString();
			System.out.println("[" + lvl + " " + time + "]("
				+ (claz.indexOf('.') >= 0 ? claz.substring(claz.lastIndexOf('.') + 1) : claz)
				+ "): " + arg0.getMessage());
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub

		}

		@Override
		public void clearFilters() {
			// TODO Auto-generated method stub

		}

		@Override
		public void addFilter(Filter arg0) {


		}
	};

	public static final Appender APPENDER_1 = new Appender(){
		String name;
		Layout l;

		@Override
		public void addFilter(Filter newFilter) {
			// TODO Auto-generated method stub

		}

		@Override
		public Filter getFilter() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void clearFilters() {
			// TODO Auto-generated method stub

		}

		@Override
		public void close() {
			// TODO Auto-generated method stub

		}

		@Override
		public void doAppend(LoggingEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setErrorHandler(ErrorHandler errorHandler) {
			// TODO Auto-generated method stub

		}

		@Override
		public ErrorHandler getErrorHandler() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setLayout(Layout layout) {
			// TODO Auto-generated method stub

		}

		@Override
		public Layout getLayout() {
			// TODO Auto-generated method stub
			return l;
		}

		@Override
		public void setName(String name) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean requiresLayout() {
			// TODO Auto-generated method stub
			return false;
		}

	};

	static Filter FILTER_CLASSNAME_REQUIRED = new Filter(){

		@Override
		public int decide(LoggingEvent event) {
			if(event.locationInformationExists() && event.getLocationInformation().getClassName() != null){
				ROOT_LOGGER.debug("FILTER_CLASSNAME_REQUIRED found a class name. returning \"NEUTRAL\".");
				return Filter.NEUTRAL;
			}
			else {
				ROOT_LOGGER.debug("FILTER_CLASSNAME_REQUIRED found no class name. returning \"DENY\".");
				return Filter.DENY;
			}
		}

	};

	static {
		APPENDER_1.addFilter(new Filter(){

			@Override
			public int decide(LoggingEvent event) {
				if(event.getLocationInformation().getClassName() != null){
					return Filter.NEUTRAL;
				}
				return Filter.DENY;
			}

		});
		APPENDER_1.addFilter(FILTER_CLASSNAME_REQUIRED);
		if(APPENDER_1.getLayout() == null){
			APPENDER_1.setLayout(new Layout(){

				@Override
				public void activateOptions() {
					// TODO Auto-generated method stub

				}

				@Override
				public String format(LoggingEvent event) {
					// TODO Auto-generated method stub
					String s = (String) event.getMessage();
					return event.getLoggerName() + s + ": ";
				}

				@Override
				public boolean ignoresThrowable() {
					// TODO Auto-generated method stub
					return false;
				}

			});
		}
	}



	/**
	 * gets the {@linkplain Logger#getRootLogger() root logger} and adds
	 * {@linkplain #APPENDER_0} to it.
	 * <p>
	 * then it prints a message to the <b>info</b> stream to show that it
	 * has been successfully set up.
	 */
	public static Logger setupLogger() {
		Logger LOG = Logger.getRootLogger();
		LOG.addAppender(APPENDER_0);
//		Logger.getRootLogger().addAppender(APPENDER_0);
		LOG.info("set up logger.");
		return LOG;
	}

	public static Logger setupLogger(Class clazz){
		Logger LOG = Logger.getLogger(clazz);
		LOG.addAppender(APPENDER_0);
		LOG.info("set up logger for " + clazz.getName());
		return LOG;
	}

	/**
	 * @see #setupLogger()
	 * @see #SetupLogger()
	 * @see Logginit
	 * @param args
	 */
	public static void main(String[] args) {
		Logger.getRootLogger().addAppender(APPENDER_1);
		setupLogger();
		Logger.getRootLogger().debug("FUUUUUUUUCK");
	}

	@Override
	public Logger getLogger() {
		return ROOT_LOGGER;
	}

	@Override
	public Logger getLogger(Class<?> whatClass) {
		return Logger.getLogger(whatClass);
	}

}
