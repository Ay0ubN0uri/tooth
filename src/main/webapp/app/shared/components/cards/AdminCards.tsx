import React from 'react';

type AdminCardsProps = {
  studentCount: number;
  groupCount: number;
  professorCount: number;
  pwsCount: number;
};

const AdminCards = ({ studentCount, groupCount, professorCount, pwsCount }: AdminCardsProps) => {
  return (
    <div className="row">
      <div className="col-xl-3 col-md-6">
        <div className="card card-stats">
          <div className="card-body">
            <div className="row">
              <div className="col">
                <h5 className="card-title text-uppercase text-muted mb-0"># of Students</h5>
                <span className="h2 font-weight-bold mb-0">{studentCount}</span>
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
      <div className="col-xl-3 col-md-6">
        <div className="card card-stats">
          <div className="card-body">
            <div className="row">
              <div className="col">
                <h5 className="card-title text-uppercase text-muted mb-0"># of Groups</h5>
                <span className="h2 font-weight-bold mb-0">{groupCount}</span>
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
      <div className="col-xl-3 col-md-6">
        <div className="card card-stats">
          <div className="card-body">
            <div className="row">
              <div className="col">
                <h5 className="card-title text-uppercase text-muted mb-0"># of Professors</h5>
                <span className="h2 font-weight-bold mb-0">{professorCount}</span>
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
      <div className="col-xl-3 col-md-6">
        <div className="card card-stats">
          <div className="card-body">
            <div className="row">
              <div className="col">
                <h5 className="card-title text-uppercase text-muted mb-0"># of PWs</h5>
                <span className="h2 font-weight-bold mb-0">{pwsCount}</span>
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
    </div>
  );
};

export default AdminCards;
