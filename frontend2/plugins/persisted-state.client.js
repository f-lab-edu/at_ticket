import createPersistedState from "vuex-persistedstate";

export default ({ store }) => {
  createPersistedState({
    key: "atticket",
    storage: window.sessionStorage,
  })(store);
};
