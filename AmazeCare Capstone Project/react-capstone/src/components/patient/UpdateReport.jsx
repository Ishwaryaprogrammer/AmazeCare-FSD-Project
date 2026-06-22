import { useDispatch } from "react-redux";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { update, getAll } from "../../store/action/reportAction";
import { useState } from "react";

const UpdateReport = () => {

    const { reportid } = useParams();
    const { state } = useLocation();
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [testName, setTestName] = useState(state?.testName || "");
    const [testDate, setTestDate] = useState(state?.date || "");
    const [file, setFile] = useState(null);

    const [msg, setMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const updateFunction = (e) => {

        e.preventDefault();

        const formData = new FormData();

        formData.append("testName", testName);
        formData.append("date", testDate);

        if (file) {
            formData.append("file", file);
        }

        dispatch(update(reportid, formData));
        dispatch(getAll(null, 0, 5));

        setMsg("Report updated successfully");

        setTimeout(() => {
            navigate(-1);
        }, 2000);
    };

    return (
        <div className="dashboard-content flex-grow-1 p-4">

            <div className="row justify-content-center">

                <div className="col-lg-8">

                    <div className="card shadow-sm border-0">

                        <div className="card-body p-4">

                            <h3 className="fw-bold text-center">
                                Update Report
                            </h3>

                            {msg &&
                                <div className="toast show align-items-center text-white bg-success border-0 w-100 mb-3">
                                    <div className="d-flex">
                                        <div className="toast-body text-center w-100">
                                            {msg}
                                        </div>
                                        <button
                                            type="button"
                                            className="btn-close btn-close-white me-2 m-auto"
                                            onClick={() => setMsg("")}
                                        ></button>
                                    </div>
                                </div>
                            }

                            {errMsg &&
                                <div className="toast show align-items-center text-white bg-danger border-0 w-100 mb-3">
                                    <div className="d-flex">
                                        <div className="toast-body text-center w-100">
                                            {errMsg}
                                        </div>
                                        <button
                                            type="button"
                                            className="btn-close btn-close-white me-2 m-auto"
                                            onClick={() => setErrMsg("")}
                                        ></button>
                                    </div>
                                </div>
                            }

                            <form onSubmit={(e)=>updateFunction(e)}>

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Test Name *
                                    </label>

                                    <input
                                        type="text"
                                        className="form-control"
                                        value={testName}
                                        onChange={(e) =>
                                            setTestName(e.target.value)
                                        }
                                        required
                                    />

                                </div>

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Test Date *
                                    </label>

                                    <input
                                        type="date"
                                        className="form-control"
                                        value={testDate}
                                        onChange={(e) =>
                                            setTestDate(e.target.value)
                                        }
                                        max={new Date().toISOString().split("T")[0]}
                                        required
                                    />

                                </div>

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Upload New File (Optional)
                                    </label>

                                    <input
                                        type="file"
                                        className="form-control"
                                        onChange={(e) => {

                                            const selectedFile = e.target.files[0];

                                            if (!selectedFile) return;

                                            if (selectedFile.size <= 5 * 1024 * 1024) {
                                                setFile(selectedFile);
                                                setErrMsg("");
                                            } else {
                                                e.target.value = "";
                                                setErrMsg(
                                                    "File size cannot be greater than 5MB"
                                                );
                                            }
                                        }}
                                    />

                                    <small className="text-muted">
                                        Allowed formats: PDF, JPG, JPEG, PNG, DOCX
                                        (Max size: 5MB)
                                    </small>

                                </div>

                                <div className="d-flex justify-content-between">

                                    <button
                                        type="button"
                                        className="btn btn-outline-secondary px-4"
                                        onClick={() => navigate(-1)}
                                    >
                                        Cancel
                                    </button>

                                    <button
                                        type="submit"
                                        className="btn btn-primary px-5"
                                    >
                                        <i className="bi bi-file-earmark-medical me-2"></i>
                                        Update Report
                                    </button>

                                </div>

                            </form>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
};

export default UpdateReport;