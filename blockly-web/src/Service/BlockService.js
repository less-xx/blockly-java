const URLS = {
    GET_BLOCK_DEFINITIONS: process.env.REACT_APP_SERVICE_BASE_URL + "/block-definitions",
    GET_TOOLBOX_CONFIG: process.env.REACT_APP_SERVICE_BASE_URL + "/toolbox-config"
}

const BlockService = {

    fetchBlockDefinitions: function (handleBlockDefs, handleError) {
        var url = URLS.GET_BLOCK_DEFINITIONS;
        fetch(url, {
            headers: {
                'Accept': 'application/json',
            },
        }).then(res => {
                return res.json();
            })
            .then(
                (result) => {
                    handleBlockDefs(result);
                })
            .catch(error => {
                if(handleError) {
                    handleError(error);
                }
            });
    },

    fetchToolbox: function (handleToolbox, handleError) {
        var url = URLS.GET_TOOLBOX_CONFIG;
        fetch(url)
            .then(response => response.text())
            .then(str => (new window.DOMParser()).parseFromString(str, "text/xml"))
            .then(
                (result) => {
                    handleToolbox(result.documentElement);
                })
            .catch(error => {
                if(handleError) {
                    handleError(error);
                }
            });
    },

};

export default BlockService;