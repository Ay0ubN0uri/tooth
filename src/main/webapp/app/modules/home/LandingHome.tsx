import PhoneMockup from 'app/shared/components/phone/PhoneMockup';
import React from 'react';
import { Container, Row, Col, Card, CardBody, CardTitle, CardText, CardImg, Button } from 'reactstrap';
import './landingpage.scss';

const LandingHome = () => {
  return (
    <>
      <Col>
        <Row>
          <Col>
            <div className="d-flex justify-content-center justify-items-center mt-4">
              <h4 className="align-self-center heading-title text-warning mb-0">
                Scan To View On <br /> Your Mobile Device
              </h4>
              <div
                style={{
                  width: '250px',
                  minWidth: '250px',
                  height: '250px',
                  position: 'relative',
                  padding: '10px',
                  borderRadius: '15px',
                  background: '#fff',
                  boxShadow: '0px 25px 40px 0px rgba(0, 0, 0, 0.15)',
                  marginLeft: '30px',
                }}
              >
                <img src="/content/images/qrcode.png" alt="Barcode" className="img-fluid" />
              </div>
            </div>
            <div className="d-flex m-5 justify-content-end">
              <Button size="lg" className="btn btn-success">
                Check Demo
              </Button>
            </div>
          </Col>

          <Col>
            {/* Features */}
            <div>
              <h1>EduDental</h1>
              <Row>
                <div className="col">
                  <div className="card card-stats">
                    <div className="card-body">
                      <div className="row">
                        <div className="col">
                          <h5 className="card-title text-uppercase text-muted mb-0">Practice Work Assignments</h5>
                          <span className="h4 font-weight-bold mb-0">
                            Assign and manage practice works for students to enhance their practical skills.
                          </span>
                        </div>
                        <div className="col-auto">
                          <div className="icon icon-shape bg-gradient-red text-white rounded-circle shadow">
                            <i className="ni ni-active-40"></i>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </Row>
              <Row>
                <div className="col">
                  <div className="card card-stats">
                    <div className="card-body">
                      <div className="row">
                        <div className="col">
                          <h5 className="card-title text-uppercase text-muted mb-0">Real-Time Taper Angle Calculator</h5>
                          <span className="h4 font-weight-bold mb-0">
                            Empower students to calculate taper angles for dental preparations in real-time.
                          </span>
                        </div>
                        <div className="col-auto">
                          <div className="icon icon-shape bg-gradient-orange text-white rounded-circle shadow">
                            <i className="ni ni-chart-pie-35"></i>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </Row>
              <Row>
                <div className="col">
                  <div className="card card-stats">
                    <div className="card-body">
                      <div className="row">
                        <div className="col">
                          <h5 className="card-title text-uppercase text-muted mb-0">Profile and Progress Tracking</h5>
                          <span className="h4 font-weight-bold mb-0">
                            Enable students to track their progress and manage their profiles within the app.
                          </span>
                        </div>
                        <div className="col-auto">
                          <div className="icon icon-shape bg-gradient-green text-white rounded-circle shadow">
                            <i className="ni ni-money-coins"></i>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </Row>
              <Row>
                <div className="col">
                  <div className="card card-stats">
                    <div className="card-body">
                      <div className="row">
                        <div className="col">
                          <h5 className="card-title text-uppercase text-muted mb-0">Interactive Learning Modules</h5>
                          <span className="h4 font-weight-bold mb-0">
                            Engage students with interactive learning modules on dental anatomy, procedures, and techniques.
                          </span>
                        </div>
                        <div className="col-auto">
                          <div className="icon icon-shape bg-gradient-info text-white rounded-circle shadow">
                            <i className="ni ni-chart-bar-32"></i>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </Row>
            </div>
          </Col>
        </Row>
      </Col>
      <Col md={4} className="mb-4 d-flex justify-content-center">
        <div className="phone-mockup">
          <PhoneMockup>
            <img src="/content/images/home.png" alt="Mobile App Image" className="device-screen" />
          </PhoneMockup>
        </div>
      </Col>
      {/* <Col md={2}></Col> */}
    </>
  );
};

export default LandingHome;

{
  /* <div className="card">
                <img className="card-img-top" src="http://localhost:5500/assets/img/theme/img-1-1000x900.jpg" alt="Image placeholder" />
                <div className="card-body">
                  <h5 className="h2 card-title mb-0">Get started with Argon</h5>
                  <small className="text-muted">by John Snow on Oct 29th at 10:23 AM</small>
                  <p className="card-text mt-4">
                    Argon is a great free UI package based on Bootstrap 4 that includes the most important components and features.
                  </p>
                  <a href="#!" className="btn btn-link px-0">
                    View article
                  </a>
                </div>
              </div> */
}
