
const Contact = () => {
  return (
    

     <div className="container py-5">

    <div className="text-center mb-5">
        <p className="text-primary fw-bold fs-5">Contact Us</p>
        <h2 className="fw-bold">Get In Touch With Us</h2>
        <p className="text-muted">
            Reach out to us for support, inquiries, and healthcare assistance.
        </p>
    </div>

    <div className="row g-4 text-center">

        <div className="col-md-3">
            <div className="card border-0 shadow-sm h-100">
                <div className="card-body">
                    <h1>📍</h1>
                    <h5 className="fw-bold">Location</h5>
                    <p className="text-muted">
                        Chennai,<br />
                        Tamil Nadu, India
                    </p>
                </div>
            </div>
        </div>

        <div className="col-md-3">
            <div className="card border-0 shadow-sm h-100">
                <div className="card-body">
                    <h1>📞</h1>
                    <h5 className="fw-bold">Phone</h5>
                    <p className="text-muted">
                        +91 7695960911
                    </p>
                </div>
            </div>
        </div>

        <div className="col-md-3">
            <div className="card border-0 shadow-sm h-100">
                <div className="card-body">
                    <h1>✉️</h1>
                    <h5 className="fw-bold">Email</h5>
                    <p className="text-muted">
                        support@amazecare.com
                    </p>
                </div>
            </div>
        </div>

        <div className="col-md-3">
            <div className="card border-0 shadow-sm h-100">
                <div className="card-body">
                    <h1>🕒</h1>
                    <h5 className="fw-bold">Hours</h5>
                    <p className="text-muted">
                        Monday - Saturday<br />
                        9:00 AM - 6:00 PM
                    </p>
                </div>
            </div>
        </div>

    </div>

</div>
  );
};

export default Contact;