
import AboutImage from "../../assets/about-image.png"

const About = () => {

    return (

            <div className="container-fluid">

            <div className="row align-items-center">
                <div className="col-sm-1"></div>
                <div className="col-lg-4 fw-bold ">
                    <p className="fw-bold fs-4 text-primary"> About us</p>
                    
                    <p className=" fs-2 ">Compassionate Care,<br/>Every Step of the Way</p>
                    <p className="fw-normal  ">Amaze HealthCare is dedicated to providing 
                        high-quality healthcare services with compassion and excellence.</p>
                    <p className="fw-normal ">Our mission is to make healthcare accessible,
                        affordable, and reliable by connection patients
                        with the right care and information.</p>
                
                </div>

                <div className="col-lg-7 text-center">
                    <img src={AboutImage} alt="Healthcare"  className="img-fluid"  />
                </div>

            </div>
            </div>




    )
}
export default About