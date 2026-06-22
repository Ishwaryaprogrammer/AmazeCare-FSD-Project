
import ImageHome from "../../assets/image-home.png"

const HomeWidget = () => {

    return (
        <div className="container-fluid"> <div className="row align-items-center">
            <div className="col-sm-1"></div>
            <div className="col-lg-3 fw-bold ">
                <p className=" fs-1">Your Health,<br /> Our Priority</p>
                <p className="fw-normal">We make healthcare simple, accessible
                    and reliable for you and your family.</p>
            </div>

            <div className="col-lg-8 text-center">
                <img
                    src={ImageHome}
                    alt="Healthcare"
                    className="img-fluid"
                />
            </div>

        </div></div>


    )
}
export default HomeWidget