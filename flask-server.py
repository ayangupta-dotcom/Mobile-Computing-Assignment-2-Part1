import flask
import werkzeug
import time

app = flask.Flask(__name__)

@app.route('/', methods = ['GET', 'POST'])
def handle_request():
    files_ids = list(flask.request.files)
    print("\nNumber of Received Video File : ", len(files_ids))
    video_num = 1
    for file_id in files_ids:
        print("\nSaving Video ", str(video_num), "/", len(files_ids))
        videofile = flask.request.files[file_id]
        filename = werkzeug.utils.secure_filename(videofile.filename)
        print("Video Filename : " + videofile.filename)
        timestr = time.strftime("%Y%m%d-%H%M%S")
        videofile.save(timestr+'_'+filename)
        video_num = video_num + 1
    print("\n")
    return "Server has been Started."

app.run(host="0.0.0.0", port=5000, debug=True , threaded=True)
