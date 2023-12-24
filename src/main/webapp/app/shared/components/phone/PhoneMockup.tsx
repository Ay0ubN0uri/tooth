import React from 'react';

const demoSlides = [
  {
    name: 'Willis',
    src: 'https://webdoodle.s3.us-east-2.amazonaws.com/callsesh/willis.jpg',
  },
  {
    name: 'Brendan',
    src: 'https://webdoodle.s3.us-east-2.amazonaws.com/callsesh/brendan.jpg',
  },
  {
    name: 'Jenny',
    src: 'https://webdoodle.s3.us-east-2.amazonaws.com/callsesh/jenny.jpg',
  },
  {
    name: 'Christie',
    src: 'https://webdoodle.s3.us-east-2.amazonaws.com/callsesh/christie.jpg',
  },
  {
    name: 'Samantha',
    src: 'https://webdoodle.s3.us-east-2.amazonaws.com/callsesh/samantha.jpg',
  },
];

const sliderSettings = {
  infinite: true,
  speed: 250,
  slidesToShow: 1,
  slidesToScroll: 1,
  autoplay: true,
  autoplaySpeed: 5000,
  arrow: false,
};

const PhoneMockup = ({ children }) => {
  return (
    <>
      <div className="device device-iphone-x">
        <div className="device-frame">
          <div className="overflow-hidden rounded-3">{children}</div>
        </div>
        <div className="device-stripe" />
        <div className="device-header" />
        <div className="device-sensors" />
        <div className="device-btns" />
        <div className="device-power" />
      </div>
      {/* <div className="device device-iphone-x">
        <div className="device-frame">
          <img
            src="https://appzilla.dexignzone.com/reactnative/welcome/images/mobile/education/home.png"
            alt="Mobile App Image"
            className="device-screen"
          />
        </div>
        <div className="device-stripe"></div>
        <div className="device-header"></div>
        <div className="device-sensors"></div>
        <div className="device-btns"></div>
        <div className="device-power"></div>
      </div> */}
    </>
  );
};
const CallPageSlider = ({ tilt }) => {};

export default PhoneMockup;
