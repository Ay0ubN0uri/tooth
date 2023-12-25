import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import React from 'react';
import { Route } from 'react-router';
import Profile from './Profile';

const ProfileRoutes = () => {
  return (
    <ErrorBoundaryRoutes>
      <Route index element={<span>hello</span>} />
      <Route path=":id">
        <Route index element={<Profile />} />
      </Route>
    </ErrorBoundaryRoutes>
  );
};

export default ProfileRoutes;
