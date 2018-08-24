function init() {
    if(nodeDataArray == null || linkDataArray == null){
        return;
    }

    //if (window.goSamples) goSamples();  // init for these samples -- you don't need to call this
    var $ = go.GraphObject.make;  // for conciseness in defining templates
    myDiagram =
        $(go.Diagram, "myDiagramDiv",  // create a Diagram for the DIV HTML element
            {
                // position the graph in the middle of the diagram
                initialContentAlignment: go.Spot.Center,
                "toolManager.mouseWheelBehavior": go.ToolManager.WheelZoom,
                layout: $(go.ForceDirectedLayout,  { maxIterations: 200, defaultSpringLength: 30, defaultElectricalCharge: 100, arrangesToOrigin: true})
            });

    function nodeInfo(d) {  // Tooltip info for a node data object
        var str = "Node " + d.key + ": " + d.text + "\n";
        if (d.group)
            str += "member of " + d.group;
        else
            str += "top-level node";
        return str;
    }
    // These nodes have text surrounded by a rounded rectangle
    // whose fill color is bound to the node data.
    // The user can drag a node by dragging its TextBlock label.
    var stepNodeTemplate =
        $(go.Node, "Horizontal",
            { locationSpot: go.Spot.Center },
              // the label shows the node data's text
            $(go.Panel, "Auto",
                $(go.Shape, "Rectangle",
                    {
                        fill: "white", // the default fill, if there is no data bound value
                        portId: "", cursor: "pointer"  // the Shape is the port, not the whole Node
                        // allow all kinds of links from and to this port
                    },
                    new go.Binding("fill", "color")),
                $(go.TextBlock,
                    {
                        font: "bold 14px sans-serif",
                        stroke: '#333',
                        margin: 6,  // make some extra space for the shape around the text
                        isMultiline: true,  // don't allow newlines in text
                        editable: false  // allow in-place editing by user
                    },
                    new go.Binding("text", "text").makeTwoWay()),  // the label shows the node data's text
                { // this tooltip Adornment is shared by all nodes
                    toolTip:
                        $(go.Adornment, "Auto",
                            $(go.Shape, { fill: "#FFFFCC" }),
                            $(go.TextBlock, { margin: 4 },  // the tooltip shows the result of calling nodeInfo(data)
                                new go.Binding("text", "", nodeInfo))
                        ),
                    // this context menu Adornment is shared by all nodes
                }),
            $(go.TextBlock,
                {
                    font: "bold 30px sans-serif",
                    stroke: '#333',
                    margin: 0,  // make some extra space for the shape around the text
                    isMultiline: true,  // don't allow newlines in text
                    editable: false,  // allow in-place editing by user
                },
                new go.Binding("text", "breakdownLabel").makeTwoWay()),
            $(go.TextBlock,
                {
                    font: "italic 14px sans-serif",
                    stroke: '#333',
                    margin: 0,  // make some extra space for the shape around the text
                    isMultiline: true,  // don't allow newlines in text
                    editable: false,  // allow in-place editing by user
                },
                new go.Binding("text", "evidence").makeTwoWay()),
        );

    var diamondNodeTemplate =
        $(go.Node, "Horizontal",
            { locationSpot: go.Spot.Center },
            // the label shows the node data's text
            $(go.Panel, "Auto",
                $(go.Shape, "Diamond",
                    {
                        fill: "white", // the default fill, if there is no data bound value
                        portId: "", cursor: "pointer"  // the Shape is the port, not the whole Node
                        // allow all kinds of links from and to this port
                    },
                    new go.Binding("fill", "color")),
                $(go.TextBlock,
                    {
                        font: "bold 14px sans-serif",
                        stroke: '#333',
                        margin: 6,  // make some extra space for the shape around the text
                        isMultiline: true,  // don't allow newlines in text
                        editable: false  // allow in-place editing by user
                    },
                    new go.Binding("text", "text").makeTwoWay()),  // the label shows the node data's text
                { // this tooltip Adornment is shared by all nodes
                    toolTip:
                        $(go.Adornment, "Auto",
                            $(go.Shape, { fill: "#FFFFCC" }),
                            $(go.TextBlock, { margin: 4 },  // the tooltip shows the result of calling nodeInfo(data)
                                new go.Binding("text", "", nodeInfo))
                        ),
                    // this context menu Adornment is shared by all nodes
                }),
            $(go.TextBlock,
                {
                    font: "bold 30px sans-serif",
                    stroke: '#333',
                    margin: 0,  // make some extra space for the shape around the text
                    isMultiline: true,  // don't allow newlines in text
                    editable: false,  // allow in-place editing by user
                },
                new go.Binding("text", "breakdownLabel").makeTwoWay()),
            $(go.TextBlock,
                {
                    font: "italic 14px sans-serif",
                    stroke: '#333',
                    margin: 0,  // make some extra space for the shape around the text
                    isMultiline: true,  // don't allow newlines in text
                    editable: false,  // allow in-place editing by user
                },
                new go.Binding("text", "evidence").makeTwoWay()),
        );

    var templmap = new go.Map("string", go.Node);
    // for each of the node categories, specify which template to use
    templmap.add("step", stepNodeTemplate);
    templmap.add("diamond", diamondNodeTemplate);
    // for the default category, "", use the same template that Diagrams use by default;
    // this just shows the key value as a simple TextBlock
    templmap.add("", stepNodeTemplate);

    myDiagram.nodeTemplateMap = templmap;

    // Define the appearance and behavior for Links:
    function linkInfo(d) {  // Tooltip info for a link data object
        return "Link:\nfrom " + d.from + " to " + d.to;
    }
    // The link shape and arrowhead have their stroke brush data bound to the "color" property
    var linkWithLabelTemplate =
        $(go.Link,
            {   curve: go.Link.Bezier,
                adjusting: go.Link.Stretch,
                toShortLength: 3 },
            new go.Binding("curviness"),
            $(go.Shape,
                { strokeWidth: 2 },
                new go.Binding("stroke", "color")),
            $(go.Shape,
                { toArrow: "Standard", stroke: null },
                new go.Binding("fill", "color")),
            $(go.Panel, "Auto",
                $(go.Shape,  // the label background, which becomes transparent around the edges
                    {
                        fill: $(go.Brush, "Radial",
                            { 0: "rgb(255, 255, 255)", 0.3: "rgb(255, 255, 255)", 1: "rgba(255, 255, 255, 0)" }),
                        stroke: null
                    }),
                $(go.TextBlock, "transition",  // the label text
                    {
                        textAlign: "center",
                        font: "13pt helvetica, arial, sans-serif",
                        margin: 4,
                        editable: true  // enable in-place editing
                    },
                    // editing the text automatically updates the model data
                    new go.Binding("text").makeTwoWay())),
            { // this tooltip Adornment is shared by all links
                toolTip:
                    $(go.Adornment, "Auto",
                        $(go.Shape, { fill: "#FFFFCC" }),
                        $(go.TextBlock, { margin: 4 },  // the tooltip shows the result of calling linkInfo(data)
                            new go.Binding("text", "", linkInfo))
                    ),
                // the same context menu Adornment is shared by all links
            }
        );

    var linkWithoutLabelTemplate =
        $(go.Link,
            {   curve: go.Link.Bezier,
                adjusting: go.Link.Stretch,
                toShortLength: 3 },
            new go.Binding("curviness"),
            $(go.Shape,
                { strokeWidth: 2 },
                new go.Binding("stroke", "color")),
            $(go.Shape,
                { toArrow: "Standard", stroke: null },
                new go.Binding("fill", "color")),
            $(go.Panel, "Auto",
                $(go.TextBlock, "transition",  // the label text
                    {
                        textAlign: "center",
                        font: "13pt helvetica, arial, sans-serif",
                        margin: 4,
                        editable: true  // enable in-place editing
                    },
                    // editing the text automatically updates the model data
                    new go.Binding("text").makeTwoWay())),
            { // this tooltip Adornment is shared by all links
                toolTip:
                    $(go.Adornment, "Auto",
                        $(go.Shape, { fill: "#FFFFCC" }),
                        $(go.TextBlock, { margin: 4 },  // the tooltip shows the result of calling linkInfo(data)
                            new go.Binding("text", "", linkInfo))
                    ),
                // the same context menu Adornment is shared by all links
            }
        );

    var linkTemplmap = new go.Map("string", go.Link);
    // for each of the node categories, specify which template to use
    linkTemplmap.add("label", linkWithLabelTemplate);
    linkTemplmap.add("nolabel", linkWithoutLabelTemplate);
    linkTemplmap.add("", linkWithoutLabelTemplate);
    myDiagram.linkTemplateMap = linkTemplmap;

    // Define the appearance and behavior for Groups:
    function groupInfo(adornment) {  // takes the tooltip or context menu, not a group node data object
        var g = adornment.adornedPart;  // get the Group that the tooltip adorns
        var mems = g.memberParts.count;
        var links = 0;
        g.memberParts.each(function(part) {
            if (part instanceof go.Link) links++;
        });
        return "Group " + g.data.key + ": " + g.data.text + "\n" + mems + " members including " + links + " links";
    }
    // Groups consist of a title in the color given by the group node data
    // above a translucent gray rectangle surrounding the member parts
    myDiagram.groupTemplate =
        $(go.Group, "Vertical",
            { selectionObjectName: "PANEL",  // selection handle goes around shape, not label
                ungroupable: true },  // enable Ctrl-Shift-G to ungroup a selected Group
            $(go.TextBlock,
                {
                    font: "bold 19px sans-serif",
                    isMultiline: true,  // don't allow newlines in text
                    editable: false  // allow in-place editing by user
                },
                new go.Binding("text", "text").makeTwoWay(),
                new go.Binding("stroke", "color")),
            $(go.Panel, "Auto",
                { name: "PANEL" },
                $(go.Shape, "Rectangle",  // the rectangular shape around the members
                    {
                        fill: "rgba(128,128,128,0.2)", stroke: "gray", strokeWidth: 3,
                        portId: "", cursor: "pointer"  // the Shape is the port, not the whole Node
                        // allow all kinds of links from and to this port
                    }),
                $(go.Placeholder, { margin: 10, background: "transparent" })  // represents where the members are
            ),
            { // this tooltip Adornment is shared by all groups
                toolTip:
                    $(go.Adornment, "Auto",
                        $(go.Shape, { fill: "#FFFFCC" }),
                        $(go.TextBlock, { margin: 4 },
                            // bind to tooltip, not to Group.data, to allow access to Group properties
                            new go.Binding("text", "", groupInfo).ofObject())
                    ),
                // the same context menu Adornment is shared by all groups
            }
        );
    // Define the behavior for the Diagram background:
    function diagramInfo(model) {  // Tooltip info for the diagram's model
        return "Model:\n" + model.nodeDataArray.length + " nodes, " + model.linkDataArray.length + " links";
    }



    myDiagram.model = new go.GraphLinksModel(nodeDataArray, linkDataArray);
}

function onFileLoad(elementId, event) {
    var fileResult = JSON.parse(event.target.result);
    nodeDataArray = fileResult.nodes;
    linkDataArray = fileResult.links;
    if(nodeDataArray == null || linkDataArray == null){
        return;
    }
    document.getElementById('sample').removeAttribute("hidden");
    try{
        redraw();
    }
    catch {
        init();
    }
}

function onChooseFile(event, onLoadFileHandler) {
    if (typeof window.FileReader !== 'function')
        throw ("The file API isn't supported on this browser.");
    let input = event.target;
    if (!input)
        throw ("The browser does not properly implement the event object");
    if (!input.files)
        throw ("This browser does not support the `files` property of the file input.");
    if (!input.files[0])
        return undefined;
    let file = input.files[0];
    let fr = new FileReader();
    fr.onload = onLoadFileHandler;
    fr.readAsText(file);
}

function redraw() {
    myDiagram.div = null;
    init();
}