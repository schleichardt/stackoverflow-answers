package controllers;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import play.*;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.mvc.*;
import views.html.index;
import scala.concurrent.Promise$;

public class Application extends Controller {
    //don't forget to tm.shutdownNow() on application termination
    //you can configure a Thread pool http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/transfer/TransferManager.html#TransferManager(com.amazonaws.services.s3.AmazonS3, java.util.concurrent.ThreadPoolExecutor)
    private static TransferManager tm;
    private static final String BUCKET_NAME = "your-bucket";

    //this is bad style, use a plugin!
    static {
        final String accessKey = System.getenv("AWS_ACCESS");
        final String secretKey = System.getenv("AWS_SECRET");
        final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        tm = new TransferManager(credentials);
    }

    /** shows a form to upload a file */
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    /** uploads a file to Amazon S3. */
    public static Promise<Result> upload() {
        final Http.MultipartFormData.FilePart meta = request().body().asMultipartFormData().getFile("picture");
        final String key = meta.getFilename();
        final Upload upload = tm.upload(BUCKET_NAME, key, meta.getFile());
        Logger.info("start upload " + meta.getFilename());
        return asPromise(meta.getFilename(), upload).map(new Function<UploadResult, Result>() {
            @Override
            public Result apply(UploadResult uploadResult) throws Throwable {
                Logger.info("finished " + meta.getFilename());
                return ok(asString(uploadResult));
            }
        });
    }

    private static String asString(UploadResult result) {
        return "UploadResult{bucketName=" + result.getBucketName() + ", key=" + result.getKey() + ", version=" + result.getVersionId() + ", ETag=" + result.getETag() + "}";
    }

    private static Promise<UploadResult> asPromise(final String filename, final Upload upload) {
        final scala.concurrent.Promise<UploadResult> scalaPromise = Promise$.MODULE$.apply();
        upload.addProgressListener(new ProgressListener() {
            @Override
            public void progressChanged(ProgressEvent progressEvent) {
                if (progressEvent.getEventCode() == ProgressEvent.CANCELED_EVENT_CODE) {
                    scalaPromise.failure(new RuntimeException("canceled " + filename));
                } else if (progressEvent.getEventCode() == ProgressEvent.FAILED_EVENT_CODE) {
                    scalaPromise.failure(new RuntimeException("failed " + filename));
                } else if(progressEvent.getEventCode() == ProgressEvent.COMPLETED_EVENT_CODE) {
                    Logger.info("done " + filename);
                    try {
                        scalaPromise.success(upload.waitForUploadResult());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return Promise.wrap(scalaPromise.future());
    }

}
